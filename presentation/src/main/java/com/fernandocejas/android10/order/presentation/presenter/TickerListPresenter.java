package com.fernandocejas.android10.order.presentation.presenter;

import android.support.annotation.NonNull;

import com.fernandocejas.android10.order.domain.Model_buyer.Address_Buyer;
import com.fernandocejas.android10.order.domain.Model_buyer.Offer_Buyer;
import com.fernandocejas.android10.order.domain.Model_buyer.Order_Buyer;
import com.fernandocejas.android10.order.domain.Model_buyer.User_Buyer;
import com.fernandocejas.android10.order.domain.Ticker;
import com.fernandocejas.android10.order.domain.Token;
import com.fernandocejas.android10.order.domain.interactor.AddTicker;
import com.fernandocejas.android10.order.domain.interactor.DeleteTicker;
import com.fernandocejas.android10.order.domain.interactor.GetListTicker;
import com.fernandocejas.android10.order.domain.interactor.GetTokenFromStripe;
import com.fernandocejas.android10.order.domain.interactor_buyer.GetAddressList_Buyer;
import com.fernandocejas.android10.order.domain.interactor_buyer.GetCompany;
import com.fernandocejas.android10.order.domain.interactor_buyer.GetMakeOffer_Buyer;
import com.fernandocejas.android10.order.domain.interactor_buyer.GetMyOrder_Buyer;
import com.fernandocejas.android10.order.domain.interactor_buyer.GetOfferList_Buyer;
import com.fernandocejas.android10.order.domain.interactor_buyer.GetOrderList_Buyer;
import com.fernandocejas.android10.order.domain.interactor_buyer.RegisterUser_Buyer;
import com.fernandocejas.android10.order.presentation.mapper.TickerModelDataMapper;
import com.fernandocejas.android10.order.presentation.utils.PreferencesUtility;
import com.fernandocejas.android10.order.presentation.view.TickerListView;
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
 * Created by vandongluong on 3/8/18.
 */

public class TickerListPresenter implements Presenter{
    private String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vNDUuNzkuMTMwLjIyMy9hcGlrb2RhL2J1eWVyL3NpZ25pbiIsImlhdCI6MTUyMTU0MTA5OSwiZXhwIjoxNTIxNTQ0Njk5LCJuYmYiOjE1MjE1NDEwOTksImp0aSI6IkdNNm8zdjMwZ1FJTXRpcUEiLCJzdWIiOjg4LCJwcnYiOiIyM2JkNWM4OTQ5ZjYwMGFkYjM5ZTcwMWM0MDA4NzJkYjdhNTk3NmY3In0.g5aaZIKmnjCNSvunfkTXow3UDbARMW3_fONK8BKNP6I";


    private TickerListView tickerListView;
    private final GetListTicker getListTickerUseCase;
    private final GetTokenFromStripe getTokenFromStripeUseCase;
    private final GetAddressList_Buyer getAddressListBuyer;
    private final GetOrderList_Buyer getOrderList_buyer;
    private final GetOfferList_Buyer getOfferList_buyer;
    private final GetMyOrder_Buyer getMyOrder_buyer;
    private final GetMakeOffer_Buyer getMakeOffer_buyer;
    private final RegisterUser_Buyer registerUser_buyer;
    private final GetCompany getCompany;
    private final AddTicker addTicker;
    private final DeleteTicker deleteTickerUser;
    private final TickerModelDataMapper tickerModelDataMapper;

    private List<Ticker> tickerList;

    private int remove_position = 0;

    @Inject
    public TickerListPresenter(GetListTicker getListTickerUseCase,
                                GetTokenFromStripe getTokenFromStripeUseCase,
                               AddTicker addTicker,
                               DeleteTicker deleteTickerUser,
                               GetAddressList_Buyer getAddressListBuyer,
                               GetOrderList_Buyer getOrderList_buyer,
                               GetOfferList_Buyer getOfferList_buyer,
                               GetMyOrder_Buyer getMyOrder_buyer,
                               GetMakeOffer_Buyer getMakeOffer_buyer,
                               RegisterUser_Buyer registerUser_buyer,
                               GetCompany getCompany,
                               TickerModelDataMapper tickerModelDataMapper) {
        this.getListTickerUseCase = getListTickerUseCase;
        this.getTokenFromStripeUseCase = getTokenFromStripeUseCase;
        this.addTicker = addTicker;
        this.deleteTickerUser = deleteTickerUser;
        this.getAddressListBuyer = getAddressListBuyer;
        this.getOrderList_buyer = getOrderList_buyer;
        this.getOfferList_buyer = getOfferList_buyer;
        this.getMakeOffer_buyer = getMakeOffer_buyer;
        this.getMyOrder_buyer = getMyOrder_buyer;
        this.registerUser_buyer = registerUser_buyer;
        this.getCompany = getCompany;
        this.tickerModelDataMapper = tickerModelDataMapper;
    }
    public void setView(@NonNull TickerListView view) {
        this.tickerListView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.getListTickerUseCase.dispose();
        this.getTokenFromStripeUseCase.dispose();
        this.tickerListView = null;
    }
    public void loadTickertList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getTickertList();
    }
    public void loadAddress() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getAddress();
    }
    public void loadOrder() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getOrder();
    }

    public void loadOffer() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getOffer();
    }


    public void loadOrderMy() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getOrderMy();
    }

    public void loadCompony(String email) {
        this.hideViewRetry();
        this.showViewLoading();
        this.getCompo(email);
    }


    public void loadMakaOffer(String orderid,
                              String deviverdate,
                              String providerfee,
                              String shipfee,
                              String surchargefee,
                              String otherfee,
                              String description) {
        this.hideViewRetry();
        this.showViewLoading();
        this.getMakeOff(orderid, deviverdate, providerfee, shipfee, surchargefee ,otherfee, description);
    }

    public void loadRegister(String name,
                             String email,
                             String password,
                             String phone,
                             String type,
                             String phone_code) {
        this.hideViewRetry();
        this.showViewLoading();
        this.getUserRegis(name, email ,password, phone, type, phone_code );
    }


    public void createTokenFromStripe(String card_number, int exp_month, int exp_year, String cvc) {
        this.hideViewRetry();
        this.showViewLoading();
        this.getTokenFromStripe(card_number, exp_month, exp_year, cvc);
    }
    public void deleteTicker(String payment_id, int position){
        this.remove_position = position;
        this.hideViewRetry();
        this.showViewLoading();
        this.fetchDeletedTicker(payment_id);
    }


    private void hideViewRetry() {
        this.tickerListView.hideRetry();
    }
    private void showViewLoading() {
        this.tickerListView.showLoading();
    }

    private void hideViewLoading() {
        this.tickerListView.hideLoading();
    }

    private void showViewRetry() {
        this.tickerListView.showRetry();
    }
    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.tickerListView.context(),
                errorBundle.getException());
        this.tickerListView.showError(errorMessage);
    }

    private void getTickertList() {
        String token = PreferencesUtility.getInstance(this.tickerListView.context())
                .readString(PreferencesUtility.PREF_TOKEN, null);
        getListTickerUseCase.execute(new TickerListPresenter.GetTicktListObserver(), GetListTicker.Params.forTicker(token));

    }


    private void getAddress() {
//        String token = PreferencesUtility.getInstance(this.tickerListView.context())
//                .readString(PreferencesUtility.PREF_TOKEN, null);
//        String token ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vNDUuNzkuMTMwLjIyMy9hcGlrb2RhL2J1eWVyL3NpZ25pbiIsImlhdCI6MTUyMTQ1NDM1MiwiZXhwIjoxNTIxNDU3OTUyLCJuYmYiOjE1MjE0NTQzNTIsImp0aSI6IkVSb096VjJqb3BnaXpCSXQiLCJzdWIiOjg4LCJwcnYiOiIyM2JkNWM4OTQ5ZjYwMGFkYjM5ZTcwMWM0MDA4NzJkYjdhNTk3NmY3In0.ri-lbp9imu5xDE7vlIDINhpOHeHR8-bPPwVjBbV-j10";
        getAddressListBuyer.execute(new GetAddListObserver(), GetAddressList_Buyer.Params.forAddress(token));

    }
    private void getOrder() {
//        String token = PreferencesUtility.getInstance(this.tickerListView.context())
//                .readString(PreferencesUtility.PREF_TOKEN, null);
//        String token ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vNDUuNzkuMTMwLjIyMy9hcGlrb2RhL2J1eWVyL3NpZ25pbiIsImlhdCI6MTUyMTQ1NDM1MiwiZXhwIjoxNTIxNDU3OTUyLCJuYmYiOjE1MjE0NTQzNTIsImp0aSI6IkVSb096VjJqb3BnaXpCSXQiLCJzdWIiOjg4LCJwcnYiOiIyM2JkNWM4OTQ5ZjYwMGFkYjM5ZTcwMWM0MDA4NzJkYjdhNTk3NmY3In0.ri-lbp9imu5xDE7vlIDINhpOHeHR8-bPPwVjBbV-j10";
        getOrderList_buyer.execute(new GetOrderListObserver(), GetOrderList_Buyer.Params.forOrder(token));

    }
    private void getOrderMy() {
//        String token = PreferencesUtility.getInstance(this.tickerListView.context())
//                .readString(PreferencesUtility.PREF_TOKEN, null);
//        String token ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vNDUuNzkuMTMwLjIyMy9hcGlrb2RhL2J1eWVyL3NpZ25pbiIsImlhdCI6MTUyMTQ1NDM1MiwiZXhwIjoxNTIxNDU3OTUyLCJuYmYiOjE1MjE0NTQzNTIsImp0aSI6IkVSb096VjJqb3BnaXpCSXQiLCJzdWIiOjg4LCJwcnYiOiIyM2JkNWM4OTQ5ZjYwMGFkYjM5ZTcwMWM0MDA4NzJkYjdhNTk3NmY3In0.ri-lbp9imu5xDE7vlIDINhpOHeHR8-bPPwVjBbV-j10";
        getMyOrder_buyer.execute(new GetOrderListObserver(), GetMyOrder_Buyer.Params.forMyOrder(token));

    }
    private void getOffer() {
//        String token = PreferencesUtility.getInstance(this.tickerListView.context())
//                .readString(PreferencesUtility.PREF_TOKEN, null);
//        String token ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vNDUuNzkuMTMwLjIyMy9hcGlrb2RhL2J1eWVyL3NpZ25pbiIsImlhdCI6MTUyMTQ1NDM1MiwiZXhwIjoxNTIxNDU3OTUyLCJuYmYiOjE1MjE0NTQzNTIsImp0aSI6IkVSb096VjJqb3BnaXpCSXQiLCJzdWIiOjg4LCJwcnYiOiIyM2JkNWM4OTQ5ZjYwMGFkYjM5ZTcwMWM0MDA4NzJkYjdhNTk3NmY3In0.ri-lbp9imu5xDE7vlIDINhpOHeHR8-bPPwVjBbV-j10";
        getOfferList_buyer.execute(new GetOfferListObserver(), GetOfferList_Buyer.Params.forOffer(token));

    }

    private void getMakeOff(String orderid,
                            String deviverdate,
                            String providerfee,
                            String shipfee,
                            String surchargefee,
                            String otherfee,
                            String description) {
//        String token = PreferencesUtility.getInstance(this.tickerListView.context())
//                .readString(PreferencesUtility.PREF_TOKEN, null);
//        String token ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vNDUuNzkuMTMwLjIyMy9hcGlrb2RhL2J1eWVyL3NpZ25pbiIsImlhdCI6MTUyMTQ1NDM1MiwiZXhwIjoxNTIxNDU3OTUyLCJuYmYiOjE1MjE0NTQzNTIsImp0aSI6IkVSb096VjJqb3BnaXpCSXQiLCJzdWIiOjg4LCJwcnYiOiIyM2JkNWM4OTQ5ZjYwMGFkYjM5ZTcwMWM0MDA4NzJkYjdhNTk3NmY3In0.ri-lbp9imu5xDE7vlIDINhpOHeHR8-bPPwVjBbV-j10";
        getMakeOffer_buyer.execute(new GetOfferMakeObserver(), GetMakeOffer_Buyer.Params.forMake(token,orderid,deviverdate,
                providerfee,shipfee, surchargefee, otherfee, description));

    }

    private void getUserRegis(String name,
                              String email,
                              String password,
                              String phone,
                              String type,
                              String phone_code) {
        registerUser_buyer.execute(new RegisterUserObserver(), RegisterUser_Buyer.Params.forUserRegister(name,email,password,phone,type,phone_code));

    }

    private void getCompo(String email) {
        getCompany.execute(new GetOfferMakeObserver(), GetCompany.Params.forCompony(token,email));

    }


    private void getTokenFromStripe(String card_number, int exp_month, int exp_year, String cvc) {
        getTokenFromStripeUseCase.execute(new TickerListPresenter.GetTokenFromStripeObserver(),
                GetTokenFromStripe.Params.forGetToken(card_number, exp_month, exp_year, cvc));
    }
    private void renderTickertList(Collection<Ticker> tickerList) {
        this.tickerListView.renderTickerMethodList(tickerModelDataMapper.transform(tickerList));
    }
    private List<Ticker> getTicker() {
        if (tickerList == null) {
            tickerList = new ArrayList<>();
        }
        return tickerList;
    }
    private void showDeletedSuccess(){
        this.tickerListView.showDeleteSuccess(this.remove_position);
    }
    private void addTicker(String stripe_token) {
        String access_token = PreferencesUtility.getInstance(this.tickerListView.context())
                .readString(PreferencesUtility.PREF_TOKEN, null);
        addTicker.execute(new TickerListPresenter.AddTickertObserver(), AddTicker.Params.forPayment(
                access_token,
                stripe_token
        ));
    }
    private void fetchDeletedTicker(String payment_id) {
        String access_token = PreferencesUtility.getInstance(this.tickerListView.context())
                .readString(PreferencesUtility.PREF_TOKEN, null);
        deleteTickerUser.execute(new TickerListPresenter.DeleteTickerObserver(), DeleteTicker.Params.forTicker(
                access_token,
                payment_id
        ));
    }





    private final class GetTicktListObserver extends DefaultObserver<List<Ticker>> {

        @Override
        public void onComplete() {
            TickerListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            //
            TickerListPresenter.this.hideViewLoading();
            TickerListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            TickerListPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(List<Ticker> tickerList) {
            TickerListPresenter.this.tickerList = tickerList;
            TickerListPresenter.this.renderTickertList(tickerList);
        }
    }
    private final class GetAddListObserver extends DefaultObserver<List<Address_Buyer>> {

        @Override
        public void onComplete() {

        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            //

        }

        @Override
        public void onNext(List<Address_Buyer> address_buyers) {

        }
    }
    private final class GetOrderListObserver extends DefaultObserver<List<Order_Buyer>> {

        @Override
        public void onComplete() {

        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            //

        }

        @Override
        public void onNext(List<Order_Buyer> order_buyers) {

        }
    }

    private final class GetOfferListObserver extends DefaultObserver<List<Offer_Buyer>> {

        @Override
        public void onComplete() {

        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            //

        }

        @Override
        public void onNext(List<Offer_Buyer> offer_buyerList) {

        }
    }
    private final class GetOfferMakeObserver extends DefaultObserver<Offer_Buyer> {

        @Override
        public void onComplete() {

        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            //

        }

        @Override
        public void onNext(Offer_Buyer offer_buyer) {

        }
    }

    private final class RegisterUserObserver extends DefaultObserver<User_Buyer> {

        @Override
        public void onComplete() {

        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            //

        }

        @Override
        public void onNext(User_Buyer user_buyer) {

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
            TickerListPresenter.this.hideViewLoading();
            TickerListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            TickerListPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(Token token) {
            TickerListPresenter.this.addTicker(token.getId());
        }
    }

    private final class AddTickertObserver extends DefaultObserver<Ticker> {

        @Override
        public void onComplete() {
            TickerListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            //
            TickerListPresenter.this.hideViewLoading();
            TickerListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            TickerListPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(Ticker ticker) {
            if (ticker != null) {
                TickerListPresenter.this.getTicker().add(ticker);
                TickerListPresenter.this.renderTickertList(tickerList);
            }
        }
    }
    private final class DeleteTickerObserver extends DefaultObserver<Ticker> {

        @Override
        public void onComplete() {
            TickerListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            //
            TickerListPresenter.this.hideViewLoading();
            TickerListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            TickerListPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(Ticker ticker) {
            TickerListPresenter.this.showDeletedSuccess();
        }
    }
}
