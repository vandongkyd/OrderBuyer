package com.fernandocejas.android10.order.presentation.presenter;

import android.support.annotation.NonNull;

import com.fernandocejas.android10.order.domain.Contact;
import com.fernandocejas.android10.order.domain.Model_buyer.Order_Buyer;
import com.fernandocejas.android10.order.domain.Ticker;
import com.fernandocejas.android10.order.domain.Token;
import com.fernandocejas.android10.order.domain.interactor.GetContactList;
import com.fernandocejas.android10.order.domain.interactor.GetListTicker;
import com.fernandocejas.android10.order.domain.interactor.GetTokenFromStripe;
import com.fernandocejas.android10.order.presentation.mapper.ContactModelDataMapper;
import com.fernandocejas.android10.order.presentation.utils.PreferencesUtility;
import com.fernandocejas.android10.order.presentation.view.ContactListView;
import com.fernandocejas.android10.sample.domain.exception.DefaultErrorBundle;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import com.fernandocejas.android10.sample.domain.interactor.DefaultObserver;
import com.fernandocejas.android10.sample.presentation.exception.ErrorMessageFactory;
import com.fernandocejas.android10.sample.presentation.presenter.Presenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by vandongluong on 3/15/18.
 */

public class ContactListPresenter implements Presenter {

    private ContactListView contactListView;
    private final GetContactList getContactListUserCase;
    private final GetTokenFromStripe getTokenFromStripeUseCase;
    private final ContactModelDataMapper contactModelDataMapper;

    private List<Contact> contactList;
    @Inject
    ContactListPresenter(GetContactList getContactListUserCase,
                         GetTokenFromStripe getTokenFromStripeUseCase,
                         ContactModelDataMapper contactModelDataMapper) {
        this.getContactListUserCase = getContactListUserCase;
        this.getTokenFromStripeUseCase = getTokenFromStripeUseCase;
        this.contactModelDataMapper = contactModelDataMapper;
    }

    public void setView(@NonNull ContactListView view) {
        this.contactListView = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getContactListUserCase.dispose();
        this.getTokenFromStripeUseCase.dispose();
        this.contactListView = null;
    }

    public void loadContact() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getContactsList();
    }
    public void createTokenFromStripe(String card_number, int exp_month, int exp_year, String cvc) {
        this.hideViewRetry();
        this.showViewLoading();
        this.getTokenFromStripe(card_number, exp_month, exp_year, cvc);
    }
    private void hideViewRetry() {
        this.contactListView.hideRetry();
    }
    private void showViewLoading() {
        this.contactListView.showLoading();
    }

    private void hideViewLoading() {
        this.contactListView.hideLoading();
    }

    private void showViewRetry() {
        this.contactListView.showRetry();
    }
    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.contactListView.context(),
                errorBundle.getException());
        this.contactListView.showError(errorMessage);
    }
    private void getContactsList() {
        String token = PreferencesUtility.getInstance(this.contactListView.context())
                .readString(PreferencesUtility.PREF_TOKEN, null);
        getContactListUserCase.execute(new ContactListPresenter.GetConttListObserver(), GetContactList.Params.forContact(token));

    }
    private void getTokenFromStripe(String card_number, int exp_month, int exp_year, String cvc) {
        getTokenFromStripeUseCase.execute(new GetTokenFromStripeObserver(),
                GetTokenFromStripe.Params.forGetToken(card_number, exp_month, exp_year, cvc));
    }
    private void renderContactList(Collection<Contact> contactList) {
        this.contactListView.renderContactMethodList(contactModelDataMapper.transform(contactList));
    }
    private List<Contact> getContact() {
        if (contactList == null) {
            contactList = new ArrayList<>();
        }
        return contactList;
    }

    private final class GetConttListObserver extends DefaultObserver<List<Contact>> {

        @Override
        public void onComplete() {
            ContactListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            //
            ContactListPresenter.this.hideViewLoading();
            ContactListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            ContactListPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(List<Contact> contactList) {
            ContactListPresenter.this.contactList = contactList;
            ContactListPresenter.this.renderContactList(contactList);
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
            ContactListPresenter.this.hideViewLoading();
            ContactListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            ContactListPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(Token token) {

        }
    }
}
