/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fernandocejas.android10.order.presentation.presenter;

import android.support.annotation.NonNull;

import com.fernandocejas.android10.order.domain.Payment;
import com.fernandocejas.android10.order.domain.Token;
import com.fernandocejas.android10.order.domain.interactor.AddPayment;
import com.fernandocejas.android10.order.domain.interactor.DeletePayment;
import com.fernandocejas.android10.order.domain.interactor.GetPaymentList;
import com.fernandocejas.android10.order.domain.interactor.GetTokenFromStripe;
import com.fernandocejas.android10.order.presentation.mapper.PaymentModelDataMapper;
import com.fernandocejas.android10.order.presentation.utils.Constants;
import com.fernandocejas.android10.order.presentation.utils.PreferencesUtility;
import com.fernandocejas.android10.order.presentation.view.PaymentListView;
import com.fernandocejas.android10.order.presentation.view.activity.PaymentListActivity;
import com.fernandocejas.android10.sample.domain.exception.DefaultErrorBundle;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.domain.interactor.DefaultObserver;
import com.fernandocejas.android10.sample.presentation.exception.ErrorMessageFactory;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import com.fernandocejas.android10.sample.presentation.presenter.Presenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class PaymentListPresenter implements Presenter {

    private PaymentListView paymentListView;

    private final GetPaymentList getPaymentListUseCase;
    private final GetTokenFromStripe getTokenFromStripeUseCase;
    private final AddPayment addPaymentUseCase;
    private final DeletePayment deletePaymentUseCase;
    private final PaymentModelDataMapper paymentModelDataMapper;

    private List<Payment> paymentList;

    private int remove_position = 0;

    @Inject
    public PaymentListPresenter(GetPaymentList getPaymentListUseCase,
                                GetTokenFromStripe getTokenFromStripeUseCase,
                                AddPayment addPaymentUseCase,
                                DeletePayment deletePaymentUseCase,
                                PaymentModelDataMapper paymentEntityDataMapper) {
        this.getPaymentListUseCase = getPaymentListUseCase;
        this.getTokenFromStripeUseCase = getTokenFromStripeUseCase;
        this.addPaymentUseCase = addPaymentUseCase;
        this.paymentModelDataMapper = paymentEntityDataMapper;
        this.deletePaymentUseCase = deletePaymentUseCase;
    }

    public void setView(@NonNull PaymentListView view) {
        this.paymentListView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.getPaymentListUseCase.dispose();
        this.getTokenFromStripeUseCase.dispose();
        this.addPaymentUseCase.dispose();
        this.paymentListView = null;
    }

    public void loadPaymentList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getPaymentList();
    }

    public void createTokenFromStripe(String card_number, int exp_month, int exp_year, String cvc) {
        this.hideViewRetry();
        this.showViewLoading();
        this.getTokenFromStripe(card_number, exp_month, exp_year, cvc);
    }

    public void deletePayment(String payment_id, int position){
        this.remove_position = position;
        this.hideViewRetry();
        this.showViewLoading();
        this.fetchDeletedPayment(payment_id);
    }

    public void goBack() {
        navigateToOrderList();
    }

    private void showViewLoading() {
        this.paymentListView.showLoading();
    }

    private void hideViewLoading() {
        this.paymentListView.hideLoading();
    }

    private void showViewRetry() {
        this.paymentListView.showRetry();
    }

    private void hideViewRetry() {
        this.paymentListView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.paymentListView.context(),
                errorBundle.getException());
        this.paymentListView.showError(errorMessage);
    }

    private void getPaymentList() {
        String token = PreferencesUtility.getInstance(this.paymentListView.context())
                .readString(PreferencesUtility.PREF_TOKEN, null);
        getPaymentListUseCase.execute(new PaymentListPresenter.GetPaymentListObserver(), GetPaymentList.Params.forPayment(token));
    }

    private void getTokenFromStripe(String card_number, int exp_month, int exp_year, String cvc) {
        getTokenFromStripeUseCase.execute(new PaymentListPresenter.GetTokenFromStripeObserver(),
                GetTokenFromStripe.Params.forGetToken(card_number, exp_month, exp_year, cvc));
    }

    private void addPayment(String stripe_token) {
        String access_token = PreferencesUtility.getInstance(this.paymentListView.context())
                .readString(PreferencesUtility.PREF_TOKEN, null);
        addPaymentUseCase.execute(new PaymentListPresenter.AddPaymentObserver(), AddPayment.Params.forPayment(
                access_token,
                stripe_token
        ));
    }

    private void fetchDeletedPayment(String payment_id) {
        String access_token = PreferencesUtility.getInstance(this.paymentListView.context())
                .readString(PreferencesUtility.PREF_TOKEN, null);
        deletePaymentUseCase.execute(new PaymentListPresenter.DeletePaymentObserver(), DeletePayment.Params.forPayment(
                access_token,
                payment_id
        ));
    }

    private void renderPaymentList(Collection<Payment> paymentList) {
        this.paymentListView.renderPaymentMethodList(paymentModelDataMapper.transform(paymentList));
    }

    private void navigateToOrderList() {
        if (this.paymentListView.activity() instanceof PaymentListActivity) {
            ((PaymentListActivity) paymentListView.activity()).navigateToOrderList();
        }
    }

    private List<Payment> getPayments() {
        if (paymentList == null) {
            paymentList = new ArrayList<>();
        }
        return paymentList;
    }

    private void showDeletedSuccess(){
        this.paymentListView.showDeleteSuccess(this.remove_position);
    }

    private final class GetPaymentListObserver extends DefaultObserver<List<Payment>> {

        @Override
        public void onComplete() {
            PaymentListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            //
            PaymentListPresenter.this.hideViewLoading();
            PaymentListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            PaymentListPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(List<Payment> paymentList) {
            PaymentListPresenter.this.paymentList = paymentList;
            PaymentListPresenter.this.renderPaymentList(paymentList);
        }
    }

    private final class GetTokenFromStripeObserver extends DefaultObserver<Token> {

        @Override
        public void onComplete() {

        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            //
            PaymentListPresenter.this.hideViewLoading();
            PaymentListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            PaymentListPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(Token token) {
            PaymentListPresenter.this.addPayment(token.getId());
        }
    }

    private final class AddPaymentObserver extends DefaultObserver<Payment> {

        @Override
        public void onComplete() {
            PaymentListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            //
            PaymentListPresenter.this.hideViewLoading();
            PaymentListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            PaymentListPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(Payment payment) {
            if (payment != null) {
                PaymentListPresenter.this.getPayments().add(payment);
                PaymentListPresenter.this.renderPaymentList(paymentList);
            }
        }
    }

    private final class DeletePaymentObserver extends DefaultObserver<Payment> {

        @Override
        public void onComplete() {
            PaymentListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            //
            PaymentListPresenter.this.hideViewLoading();
            PaymentListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            PaymentListPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(Payment payment) {
            PaymentListPresenter.this.showDeletedSuccess();
        }
    }

}
