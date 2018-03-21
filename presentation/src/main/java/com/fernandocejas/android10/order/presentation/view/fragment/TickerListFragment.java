package com.fernandocejas.android10.order.presentation.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.presentation.internal.di.components.OrderComponent;
import com.fernandocejas.android10.order.presentation.model.PaymentModel;
import com.fernandocejas.android10.order.presentation.model.TickerModel;
import com.fernandocejas.android10.order.presentation.presenter_buyer.SignInPresenter_Buyer;
import com.fernandocejas.android10.order.presentation.presenter.TickerListPresenter;
import com.fernandocejas.android10.order.presentation.utils.DialogFactory;
import com.fernandocejas.android10.order.presentation.view.TickerListView;
import com.fernandocejas.android10.order.presentation.view.adapter.ItemTouchHelperCallback;
import com.fernandocejas.android10.order.presentation.view.adapter.TickerAdapter;
import com.fernandocejas.android10.order.presentation.view.dialog.AddPaymentDialog;
import com.fernandocejas.android10.sample.presentation.view.fragment.BaseFragment;
import com.loopeer.itemtouchhelperextension.ItemTouchHelperExtension;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vandongluong on 3/8/18.
 */

public class TickerListFragment extends BaseFragment implements TickerListView {
    @Inject
    TickerListPresenter tickerListPresenter;
    @Inject
    TickerAdapter tickerAdapter;

    @Inject
    SignInPresenter_Buyer signInPresenter_buyer;


    @Bind(R.id.rv_ticker)
    RecyclerView rv_ticker;

    private ProgressDialog progressDialog;
    private AddPaymentDialog addPaymentDialog;

    private TickerAdapter.OnItemClickListener onItemClickListener = new TickerAdapter.OnItemClickListener() {
        @Override
        public void onItemClicked(TickerModel tickerModel) {

        }

        @Override
        public void onItemRemoved() {

        }

        @Override
        public void onLinkClicked(TickerModel tickerModel) {

        }

    };
    public TickerListFragment() {
        setRetainInstance(true);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(OrderComponent.class).inject(this);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_ticker_list, container, false);
        ButterKnife.bind(this, fragmentView);
        setupRecycleView();
        //TestSign();
//        tickerListPresenter.loadContact();
//        new AsyncTask<Void, Void, String>() {
//            @Override
//            protected String doInBackground(Void... voids) {
//                OkHttpClient client = new OkHttpClient();
//                Request request = new Request.Builder()
//                        .url("https://api.androidhive.info/contacts/")
//                        .build();
//
//                try {
//                    Response response = client.newCall(request).execute();
//                    Log.d(TAG,"Background" +" prams = [" + response.body().string() + "]" );
//                    return response.body().string();
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//                return null;
//            }
//        }.execute();

        return fragmentView;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.tickerListPresenter.setView(this);
        if (savedInstanceState == null) {
            this.tickerListPresenter.loadTickertList();
            //this.tickerListPresenter.loadContact();
            this.tickerListPresenter.loadAddress();
            this.tickerListPresenter.loadOrder();
            this.tickerListPresenter.loadOffer();
            //this.tickerListPresenter.loadOrderAc();
            this.tickerListPresenter.loadOrderMy();
            String orderid = "165";
            String deviverdate = "03/19/2018";
            String providerfee = "10";
            String shipfee ="10";
            String surchargefee ="";
            String otherfee ="";
            String description ="Test make offer";
            this.tickerListPresenter.loadMakaOffer(orderid,deviverdate,providerfee,shipfee,surchargefee,otherfee,description);




            String name = "ABC Buyer";
            String email = "abcbuyer@abc.com";
            String password = "123456";
            String phone = "01652759680";
            String type = "2";
            String phone_code = "+84";
            this.tickerListPresenter.loadRegister(name,email,password,phone,type,phone_code);
            this.tickerListPresenter.loadCompony(email);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        this.tickerListPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.tickerListPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.tickerListPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.tickerListPresenter = null;
    }

    @Override
    public void renderTickerMethodList(Collection<TickerModel> tickerModels) {
        this.tickerAdapter.setTickerCollection(tickerModels);
    }
    @Override
    @OnClick(R.id.btn_add_ticker)
    public void onAddTickerClicked() {
        if (this.addPaymentDialog == null) {
            this.addPaymentDialog = new AddPaymentDialog(activity());
            this.addPaymentDialog.setOnClickListener(onDialogClickListener);
        }
        this.addPaymentDialog.show();
    }

    @Override
    public void onBackClicked() {

    }

    @Override
    public void onSaveTickerInDialogClicked(String card_number, int exp_month, int exp_year, String cvc) {
        this.tickerListPresenter.createTokenFromStripe(card_number, exp_month, exp_year, cvc);
    }

    @Override
    public void onTickerRemoveClicked(PaymentModel paymentModel, int position) {

    }

    @Override
    public void showDeleteSuccess(int position) {

    }

    @Override
    public void showLoading() {
        if (progressDialog == null) {
            progressDialog = DialogFactory.createProgressDialog(activity(), R.string.processing);
        }
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {
        Toast.makeText(activity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }

    @Override
    public Activity activity() {
        return getActivity();
    }

    private AddPaymentDialog.OnClickListener onDialogClickListener = new AddPaymentDialog.OnClickListener() {
        @Override
        public void onSaveClicked(String card_number, int exp_month, int exp_year, String cvc) {
            TickerListFragment.this.onSaveTickerInDialogClicked(card_number, exp_month, exp_year, cvc);
        }

        @Override
        public void onDismissClicked() {

        }
    };

    private void setupRecycleView() {
        this.tickerAdapter.setOnItemClickListener(onItemClickListener);
        this.rv_ticker.setLayoutManager(new LinearLayoutManager(context()));
        this.rv_ticker.setAdapter(tickerAdapter);
        this.initSwipe();
    }

    private void initSwipe() {
        ItemTouchHelperCallback callback = new ItemTouchHelperCallback();
        ItemTouchHelperExtension itemTouchHelper = new ItemTouchHelperExtension(callback);
        itemTouchHelper.attachToRecyclerView(rv_ticker);
        tickerAdapter.setItemTouchHelperExtension(itemTouchHelper);
    }
//    private void TestSign(){
//        String email = "dongbuyer@abc.com";
//        String password = "123456";
//        signInPresenter_buyer.callSignIn_buyer(email, password);
//    }
}

