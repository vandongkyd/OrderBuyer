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

import com.fernandocejas.android10.order.domain.Offer;
import com.fernandocejas.android10.order.domain.Payment;
import com.fernandocejas.android10.order.domain.interactor.GetPaymentList;
import com.fernandocejas.android10.order.presentation.mapper.OfferModelDataMapper;
import com.fernandocejas.android10.order.presentation.mapper.PaymentModelDataMapper;
import com.fernandocejas.android10.order.presentation.model.PaymentModel;
import com.fernandocejas.android10.order.presentation.utils.Constants;
import com.fernandocejas.android10.order.presentation.utils.PreferencesUtility;
import com.fernandocejas.android10.order.presentation.utils.Utils;
import com.fernandocejas.android10.order.presentation.view.AcceptedView;
import com.fernandocejas.android10.order.presentation.view.activity.AcceptedActivity;
import com.fernandocejas.android10.sample.domain.exception.DefaultErrorBundle;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.domain.interactor.DefaultObserver;
import com.fernandocejas.android10.sample.presentation.exception.ErrorMessageFactory;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import com.fernandocejas.android10.sample.presentation.presenter.Presenter;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class AcceptedPresenter implements Presenter {

    private AcceptedView acceptedView;

    private final GetPaymentList getPaymentListUseCase;
    private final OfferModelDataMapper offerModelDataMapper;
    private final PaymentModelDataMapper paymentModelDataMapper;

    private Offer offer;
    private Payment payment;
    private String currency;
    private int quantity;
    private float amount;
    private float weight;
    private float sale_tax;
    private float service_fee;
    private float provider_fee;
    private float ship_fee;
    private float surcharge_fee;
    private float other_fee;
    private float total_fee;

    @Inject
    public AcceptedPresenter(GetPaymentList getPaymentListUseCase,
                             OfferModelDataMapper offerModelDataMapper,
                             PaymentModelDataMapper paymentEntityDataMapper) {
        this.getPaymentListUseCase = getPaymentListUseCase;
        this.offerModelDataMapper = offerModelDataMapper;
        this.paymentModelDataMapper = paymentEntityDataMapper;
    }

    public void setView(@NonNull AcceptedView view) {
        this.acceptedView = view;
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
        this.acceptedView = null;
    }

    private void showViewLoading() {
        this.acceptedView.showLoading();
    }

    private void hideViewLoading() {
        this.acceptedView.hideLoading();
    }

    private void showViewRetry() {
        this.acceptedView.showRetry();
    }

    private void hideViewRetry() {
        this.acceptedView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.acceptedView.context(),
                errorBundle.getException());
        this.acceptedView.showError(errorMessage);
    }

    private void showOffer(Offer offer) {
        if (offer != null) {
            this.acceptedView.showOfferInView(offerModelDataMapper.transform(offer));
        }
    }

    private void showQuantity(int quantity) {
        this.acceptedView.showQuantityInView(quantity + "");
    }

    private void showAmount(float amount, String currency) {
        this.acceptedView.showAmountInView(getPriceWithSymbolCurrency(amount + "", currency));
    }

    private void showWeight(float weight, String currency) {
        this.acceptedView.showWeightInView(getPriceWithSymbolCurrency(weight + "", currency));
    }

    private void showSale_tax(float sale_tax, String currency) {
        this.acceptedView.showSaleTaxInView(getPriceWithSymbolCurrency(sale_tax + "", currency));
    }

    private void showService_fee(float service_fee, String currency) {
        this.acceptedView.showServiceFeeInView(getPriceWithSymbolCurrency(service_fee + "", currency));
    }

    private void renderPaymentList(Collection<Payment> paymentList) {
        this.acceptedView.renderPaymentList(paymentModelDataMapper.transform(paymentList));
    }

    private void showPaymentList(){
        this.acceptedView.showPaymentList();
    }

    private void showTotal_fee(float total_fee, String currency){
        this.acceptedView.showTotalFeeInView(getPriceWithSymbolCurrency(total_fee + "", currency));
    }

    private float getTotal() {
        return amount + sale_tax + service_fee + provider_fee + ship_fee + surcharge_fee + other_fee;

    }

    public void setOffer(Offer offer) {
        this.offer = offer;
        try {
            this.provider_fee = Float.valueOf(offer.getProviderfee());
        } catch (Exception ex) {
        }
        try {
            this.ship_fee = Float.valueOf(offer.getShipfee());
        } catch (Exception ex) {
        }
        try {
            this.surcharge_fee = Float.valueOf(offer.getSurchargefee());
        } catch (Exception ex) {
        }
        try {
            this.other_fee = Float.valueOf(offer.getOtherfee());
        } catch (Exception ex) {
        }
        this.showOffer(offer);
    }

    public void setPayment(PaymentModel payment) {
        if(payment!= null){
            this.payment = paymentModelDataMapper.transform(payment);
        }
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.showQuantity(quantity);
    }

    public void setAmount(float amount) {
        this.amount = amount;
        this.showAmount(amount, currency);
    }

    public void setWeight(float weight) {
        this.weight = weight;
        this.showWeight(weight, currency);
    }

    public void setSale_tax(float sale_tax) {
        this.sale_tax = sale_tax;
        this.showSale_tax(sale_tax, currency);
    }

    public void setService_fee(float service_fee) {
        this.service_fee = service_fee;
        this.showService_fee(service_fee, currency);
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setTotal_fee() {
        this.total_fee = getTotal();
        this.showTotal_fee(total_fee, currency);
    }

    public void loadPaymentList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getPaymentList();
    }

    public String getPriceWithSymbolCurrency(String price) {
        return getPriceWithSymbolCurrency(price, this.currency);
    }

    public void goBack() {
        navigateToOrderDetail();
    }

    private void getPaymentList() {
        String token = PreferencesUtility.getInstance(this.acceptedView.context())
                .readString(PreferencesUtility.PREF_TOKEN, null);
        getPaymentListUseCase.execute(new GetPaymentListObserver(), GetPaymentList.Params.forPayment(token));
    }

    private String getPriceWithSymbolCurrency(String price, String currency) {
        String full_price = "";
        String symbol = Utils.getCurrencySymbol(currency);
        full_price = symbol + " " + price;
        return full_price;
    }

    private void navigateToOrderDetail() {
        if (this.acceptedView.activity() instanceof AcceptedActivity) {
            ((AcceptedActivity) acceptedView.activity()).navigateToOrderDetail();
        }
    }

    private final class GetPaymentListObserver extends DefaultObserver<List<Payment>> {

        @Override
        public void onComplete() {
            AcceptedPresenter.this.hideViewLoading();
            AcceptedPresenter.this.showPaymentList();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            //
            AcceptedPresenter.this.hideViewLoading();
            AcceptedPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            AcceptedPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(List<Payment> paymentList) {
            AcceptedPresenter.this.renderPaymentList(paymentList);
        }
    }
}
