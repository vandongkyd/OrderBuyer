/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.domain.Offer;
import com.fernandocejas.android10.order.presentation.internal.di.components.OrderComponent;
import com.fernandocejas.android10.order.presentation.model.AddressModel;
import com.fernandocejas.android10.order.presentation.model.OfferModel;
import com.fernandocejas.android10.order.presentation.model.PaymentModel;
import com.fernandocejas.android10.order.presentation.model.RateModel;
import com.fernandocejas.android10.order.presentation.presenter.AcceptedPresenter;
import com.fernandocejas.android10.order.presentation.utils.DialogFactory;
import com.fernandocejas.android10.order.presentation.utils.Utils;
import com.fernandocejas.android10.order.presentation.view.AcceptedView;
import com.fernandocejas.android10.order.presentation.view.adapter.PaymentSpinnerAdapter;
import com.fernandocejas.android10.sample.presentation.view.fragment.BaseFragment;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 *
 */
public class AcceptedFragment extends BaseFragment implements AcceptedView {

    @Inject
    AcceptedPresenter acceptedPresenter;
    @Inject
    PaymentSpinnerAdapter paymentSpinnerAdapter;

    @Bind(R.id.imv_logo)
    ImageView imv_logo;
    @Bind(R.id.tv_name)
    TextView tv_name;
    @Bind(R.id.rating)
    AppCompatRatingBar rating;
    @Bind(R.id.tv_rating_count)
    TextView tv_rating_count;
    @Bind(R.id.tv_address_)
    TextView tv_address_;
    @Bind(R.id.tv_delivery_date)
    TextView tv_delivery_date;
    @Bind(R.id.tv_quantity)
    TextView tv_quantity;
    @Bind(R.id.tv_amount)
    TextView tv_amount;
    @Bind(R.id.tv_weight)
    TextView tv_weight;
    @Bind(R.id.tv_sales_tax)
    TextView tv_sales_tax;
    @Bind(R.id.tv_service_fee)
    TextView tv_service_fee;
    @Bind(R.id.tv_buy_fee)
    TextView tv_buy_fee;
    @Bind(R.id.tv_ship_fee)
    TextView tv_ship_fee;
    @Bind(R.id.tv_surcharge_fee)
    TextView tv_surcharge_fee;
    @Bind(R.id.tv_other_fee)
    TextView tv_other_fee;
    @Bind(R.id.tv_total_fee)
    TextView tv_total_fee;
    @Bind(R.id.spn_payment)
    Spinner spn_payment;

    private ProgressDialog progressDialog;
    private Handler h = null;
    private Runnable r = null;

    public AcceptedFragment() {
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
        final View fragmentView = inflater.inflate(R.layout.fragment_accepted, container, false);
        ButterKnife.bind(this, fragmentView);
        setupSpinnerView();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.acceptedPresenter.setView(this);
        if (savedInstanceState == null) {
            h = new Handler();
            //
            Bundle arguments = getArguments();

            String currency = arguments.getString("args_currency");
            this.acceptedPresenter.setCurrency(currency);
            //
            int quantity = 0;
            try {
                quantity = Integer.valueOf(arguments.getString("args_quantity"));
            } catch (Exception ex) {
            }
            this.acceptedPresenter.setQuantity(quantity);
            //
            float amount = 0;
            try {
                amount = Float.valueOf(arguments.getString("args_amount"));
            } catch (Exception ex) {
            }
            this.acceptedPresenter.setAmount(amount);
            //
            float weight = 0;
            try {
                weight = Float.valueOf(arguments.getString("args_weight"));
            } catch (Exception ex) {
            }
            this.acceptedPresenter.setWeight(weight);
            //
            float sale_tax = 0;
            try {
                sale_tax = Float.valueOf(arguments.getString("args_sale_tax"));
            } catch (Exception ex) {
            }
            this.acceptedPresenter.setSale_tax(sale_tax);
            //
            float service_fee = 0;
            try {
                service_fee = Float.valueOf(arguments.getString("args_service_fee"));
            } catch (Exception ex) {
            }
            this.acceptedPresenter.setService_fee(service_fee);
            //
            Offer offer = (Offer) arguments.getSerializable("args_offer");
            this.acceptedPresenter.setOffer(offer);
            //
            this.acceptedPresenter.setTotal_fee();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.acceptedPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.acceptedPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.acceptedPresenter.destroy();
        this.h.removeCallbacks(r);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.acceptedPresenter = null;
        this.h = null;
    }

    @Override
    public void showOfferInView(OfferModel offer) {
        //show logo
        showLogoInView(offer.getLogo());
        //show name
        showNameInView(offer.getName());
        //show rating
        showRatingInView(offer.getRate());
        //show address
        String address_full = getAddress(offer.getAddress());
        showAddressInView(address_full);
        //show delivery date
        String delivery_date = getDeliveryDate(offer.getDeviverdate());
        showDeliveryDateInView(delivery_date);
        //render payment list
        //show buy fee
        showBuyFeeInView(this.acceptedPresenter.getPriceWithSymbolCurrency(offer.getProviderfee()));
        //show ship fee
        showShipFeeInView(this.acceptedPresenter.getPriceWithSymbolCurrency(offer.getShipfee()));
        //show surcharge fee
        showSurfaceFeeInView(this.acceptedPresenter.getPriceWithSymbolCurrency(offer.getSurchargefee()));
        //show other fee
        showOtherFeeInView(this.acceptedPresenter.getPriceWithSymbolCurrency(offer.getOtherfee()));
        //show total fee
    }

    @Override
    public void showLogoInView(String url) {
        Glide.with(context()).load(url).into(imv_logo);
    }

    @Override
    public void showNameInView(String name) {
        tv_name.setText(name);
    }

    @Override
    public void showRatingInView(RateModel rate) {
        if (rate != null) {
            rating.setRating(getRating(rate.getStart()));
            tv_rating_count.setText(rate.getCount());
        }
    }

    @Override
    public void showAddressInView(String address) {
        tv_address_.setText(address);
    }

    @Override
    public void showDeliveryDateInView(String date) {
        tv_delivery_date.setText(date);
    }

    @Override
    public void renderPaymentList(Collection<PaymentModel> paymentModels) {
        paymentSpinnerAdapter.replaceWith(paymentModels);
    }

    @Override
    public void showQuantityInView(String quantity) {
        tv_quantity.setText(quantity);
    }

    @Override
    public void showAmountInView(String amount) {
        tv_amount.setText(amount);
    }

    @Override
    public void showWeightInView(String weight) {
        tv_weight.setText(weight);
    }

    @Override
    public void showSaleTaxInView(String sale_tax) {
        tv_sales_tax.setText(sale_tax);
    }

    @Override
    public void showServiceFeeInView(String service_fee) {
        tv_service_fee.setText(service_fee);
    }

    @Override
    public void showBuyFeeInView(String buy_fee) {
        tv_buy_fee.setText(buy_fee);
    }

    @Override
    public void showShipFeeInView(String ship_fee) {
        tv_ship_fee.setText(ship_fee);
    }

    @Override
    public void showSurfaceFeeInView(String surface_fee) {
        tv_surcharge_fee.setText(surface_fee);
    }

    @Override
    public void showOtherFeeInView(String other_fee) {
        tv_other_fee.setText(other_fee);
    }

    @Override
    public void showTotalFeeInView(String total_fee) {
        tv_total_fee.setText(total_fee);
    }

    @Override
    @OnClick(R.id.cmd_payment)
    public void onChoicePaymentClicked() {
        this.acceptedPresenter.loadPaymentList();
    }

    @Override
    public void showPaymentList() {
        if (r == null) {
            r = () -> {
                while (true) {
                    if (progressDialog == null) {
                        break;
                    } else if (progressDialog != null && !progressDialog.isShowing()) {
                        break;
                    }
                }
                try {
                    // Open the Spinner...
                    spn_payment.performClick();
                } catch (Exception ex) {
                }
            };
        }
        h.post(r);
    }

    @Override
    @OnClick(R.id.btn_back)
    public void onBackClicked() {
        this.acceptedPresenter.goBack();
    }

    @Override
    public void onItemPaymentSelected(PaymentModel paymentModel) {
        this.acceptedPresenter.setPayment(paymentModel);
    }

    @Override
    public void onConFirmClicked() {

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
        Toast.makeText(context(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }

    @Override
    public Activity activity() {
        return getActivity();
    }

    private void setupSpinnerView() {
        this.spn_payment.setAdapter(paymentSpinnerAdapter);
        this.paymentSpinnerAdapter.setOnItemClicked(onPaymentOnItemSelectedListener);
    }

    private PaymentSpinnerAdapter.OnItemClicked onPaymentOnItemSelectedListener = paymentModel -> AcceptedFragment.this.onItemPaymentSelected(paymentModel);

    private int getRating(String rating) {
        int r = 0;
        try {
            r = Integer.valueOf(rating);
        } catch (Exception ex) {
        }
        return r;
    }

    private String getAddress(AddressModel addressModel) {
        String a = "";
        if (addressModel == null) {
            return a;
        }
        String address = addressModel.getAddress();
        String city = addressModel.getCity();
        String country = addressModel.getCountry();
        if (address != null) {
            a += address;
        }
        if (city != null) {
            if (!a.isEmpty()) {
                a += " ";
            }
            a += city;
        }
        if (country != null) {
            if (!a.isEmpty()) {
                a += " ";
            }
            a += country;
        }
        return a;
    }

    private String getDeliveryDate(String time) {
        return Utils.getTimeByFormat(time);
    }
}
