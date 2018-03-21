/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.presentation.internal.di.components.OrderComponent;
import com.fernandocejas.android10.order.presentation.model.AddressModel;
import com.fernandocejas.android10.order.presentation.model.OrderModel;
import com.fernandocejas.android10.order.presentation.model.ProductModel;
import com.fernandocejas.android10.order.presentation.presenter.OrderDetailAcceptedPresenter;
import com.fernandocejas.android10.order.presentation.utils.Constants;
import com.fernandocejas.android10.order.presentation.utils.Utils;
import com.fernandocejas.android10.order.presentation.view.OrderDetailAcceptedView;
import com.fernandocejas.android10.order.presentation.view.adapter.ProductAdapter;
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
public class OrderDetailAcceptedFragment extends BaseFragment implements OrderDetailAcceptedView {

    @Inject
    OrderDetailAcceptedPresenter orderDetailAcceptedPresenter;
    @Inject
    ProductAdapter productAdapter;

    @Bind(R.id.v_description_padding)
    View v_description_padding;
    @Bind(R.id.tv_description)
    TextView tv_description;
    @Bind(R.id.rl_progress)
    RelativeLayout rl_progress;
    @Bind(R.id.rl_retry)
    RelativeLayout rl_retry;
    @Bind(R.id.lyt_main)
    LinearLayout lyt_main;
    @Bind(R.id.rv_products)
    RecyclerView rv_products;
    @Bind(R.id.tv_delivery_date_front)
    TextView tv_delivery_date_front;
    @Bind(R.id.imv_status)
    ImageView imv_status;
    @Bind(R.id.imv_from_avatar)
    ImageView imv_from_avatar;
    @Bind(R.id.tv_from_name)
    TextView tv_from_name;
    @Bind(R.id.tv_from_address)
    TextView tv_from_address;
    @Bind(R.id.imv_to_avatar)
    ImageView imv_to_avatar;
    @Bind(R.id.tv_to_name)
    TextView tv_to_name;
    @Bind(R.id.tv_to_address)
    TextView tv_to_address;
    @Bind(R.id.tv_order_date)
    TextView tv_order_date;
    @Bind(R.id.tv_delivery_date_in_status)
    TextView tv_delivery_date_in_status;
    @Bind(R.id.imv_plane)
    ImageView imv_plane;
    @Bind(R.id.tv_order_number)
    TextView tv_order_number;
    @Bind(R.id.tv_total_items)
    TextView tv_total_items;
    @Bind(R.id.tv_price)
    TextView tv_price;
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
    @Bind(R.id.tv_date_time_sold)
    TextView tv_date_time_sold;
    @Bind(R.id.tv_address)
    TextView tv_address;

    private String order_id;

    public OrderDetailAcceptedFragment() {
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
        final View fragmentView = inflater.inflate(R.layout.fragment_order_detail_accepted, container, false);
        ButterKnife.bind(this, fragmentView);
        setupRecycleView();
        setupPlaneImageView();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.orderDetailAcceptedPresenter.setView(this);
        if (savedInstanceState == null) {
            Bundle arguments = getArguments();
            order_id = arguments.getString("args_id");
            loadOrder(order_id);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.orderDetailAcceptedPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.orderDetailAcceptedPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.orderDetailAcceptedPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.orderDetailAcceptedPresenter = null;
        this.onItemProductClickListener = null;
    }

    @Override
    public void showLoading() {
        this.lyt_main.setVisibility(View.GONE);
        this.rl_progress.setVisibility(View.VISIBLE);
        this.getActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void hideLoading() {
        this.rl_progress.setVisibility(View.GONE);
        this.getActivity().setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void showRetry() {
        this.rl_retry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        this.rl_retry.setVisibility(View.GONE);
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

    ProductAdapter.OnItemClickListener onItemProductClickListener = new ProductAdapter.OnItemClickListener() {

        @Override
        public void onItemClicked(ProductModel productModel) {
            OrderDetailAcceptedFragment.this.showProductDetail(productModel);
        }

        @Override
        public void onItemRemoved() {

        }

        @Override
        public void onLinkClicked(ProductModel productModel) {
            OrderDetailAcceptedFragment.this.onLinkClicked(productModel);
        }
    };

    private void setupPlaneImageView() {
        imv_plane.setScaleX(-1f);
    }

    private void setupRecycleView() {
        //for product list
        productAdapter.setOnItemClickListener(onItemProductClickListener);
        this.rv_products.setLayoutManager(new LinearLayoutManager(context()));
        this.rv_products.setAdapter(productAdapter);
        ((SimpleItemAnimator) this.rv_products.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    private String getDeliveryDate(String date) {
        return Utils.getTimeByFormat(date);
    }

    private String getOrderDate(String date) {
        return Utils.getTimeAgos(date);
    }

    private String getPriceWithSymbolCurrency(String amount, String currency) {
        String full_price = "";
        String symbol = Utils.getCurrencySymbol(currency);
        full_price = symbol + " " + amount;
        return full_price;
    }

    private String getWeightWithUnit(String weight, String unit) {
        return weight + " " + unit;
    }

    private String getDeliveryTo(AddressModel addressModel) {
        String deliverTo = "";
        String city = addressModel.getCity();
        String country = addressModel.getCountry();

        if (city != null) {
            deliverTo += city;
        }
        if (country != null) {
            if (!deliverTo.isEmpty()) {
                deliverTo += " ";
            }
            deliverTo += country;
        }
        return deliverTo;
    }

    private String getAddress(AddressModel addressModel) {
        String address = "";
        String city = addressModel.getCity();
        String country = addressModel.getCountry();

        if (city != null) {
            address += city;
        }
        if (country != null) {
            if (!address.isEmpty()) {
                address += " ";
            }
            address += country;
        }
        return address;
    }

    private void loadOrder(String id) {
        this.orderDetailAcceptedPresenter.loadOrderDetail(id);
    }

    private void loadImageFromUrl(Context context, ImageView view, String url, boolean isCircle, boolean hasDefault) {
        if (url == null || url.isEmpty()) {
            if (hasDefault) {
                //show default avatar if we don't have url to show
                Glide.with(context)
                        .load(R.drawable.default_avatar)
                        .asBitmap()
                        .into(new BitmapImageViewTarget(view) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                view.setImageDrawable(circularBitmapDrawable);
                            }
                        });
            }
            return;
        }
        if (isCircle) {
            Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .into(new BitmapImageViewTarget(view) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            view.setImageDrawable(circularBitmapDrawable);
                        }
                    });
        } else {
            Glide.with(context)
                    .load(url)
                    .into(view);
        }
    }

    @OnClick(R.id.bt_retry)
    void onButtonRetryClick() {
        OrderDetailAcceptedFragment.this.loadOrder(order_id);
    }

    @Override
    public void showOrderDetailInView(OrderModel orderModel) {

        this.productAdapter.setCurrency_default(orderModel.getCurrency());

        //show order
        showOrderUserInView(Constants.USER_AVATAR, Constants.USER_NAME, orderModel.getAddressModel());

        //show status
        showStatusInView(orderModel.getStatus());

        //show date & time sold
        showDateTimeSoldInView(orderModel.getDeviverdate());

        //show delivery address
        showDeliveryAddressInView(orderModel.getAddressModel());

        //show product list
        Collection<ProductModel> productModels = orderModel.getProducts();
        if (productModels != null && !productModels.isEmpty()) {
            renderProductList(productModels);
        }

        //show description
        String description = orderModel.getDescription();
        showDescriptionInView(description);

        //show order date
        String order_date = getOrderDate(orderModel.getCreated_at());
        showOrderDateInView(order_date);

        //show delivery date
        String delivery_date = getDeliveryDate(orderModel.getDeviverdate());
        showDeliveryDateInView(delivery_date);

        //show order number
        String order_number = orderModel.getId();
        showOrderNumberInView(order_number);

        //show total items
        showTotalItemsInView(orderModel.getQuantity());

        //show price fee
        String full_price = getPriceWithSymbolCurrency(orderModel.getAmount(), orderModel.getCurrency());
        showPriceFeeInView(full_price);

        //show sales tax fee
        String tax_in_percent = orderModel.getTax();
        showSaleTaxFeeInView(tax_in_percent + "%");

        //show service fee
        full_price = getPriceWithSymbolCurrency(orderModel.getServicefee(), orderModel.getCurrency());
        showServiceFeeInView(full_price);

        //show buyer fee
        full_price = getPriceWithSymbolCurrency(orderDetailAcceptedPresenter.getProvider_fee() + "", orderModel.getCurrency());
        showBuyerFreeInView(full_price);

        //show ship fee
        full_price = getPriceWithSymbolCurrency(orderDetailAcceptedPresenter.getShip_fee() + "", orderModel.getCurrency());
        showShipFeeInView(full_price);

        //show surcharge fee
        full_price = getPriceWithSymbolCurrency(orderDetailAcceptedPresenter.getSurcharge_fee() + "", orderModel.getCurrency());
        showSurchargeFeeInView(full_price);

        //show other fee
        full_price = getPriceWithSymbolCurrency(orderDetailAcceptedPresenter.getOther_fee() + "", orderModel.getCurrency());
        showOtherFeeInView(full_price);

        //show total fee
        full_price = getPriceWithSymbolCurrency(orderDetailAcceptedPresenter.getTotal() + "", orderModel.getCurrency());
        showTotalInView(full_price);

        //
        lyt_main.setVisibility(View.VISIBLE);
    }

    @Override
    public void renderProductList(Collection<ProductModel> productModels) {
        productAdapter.setProductsCollection(productModels);
    }

    @Override
    public void showDescriptionInView(String description) {
        if (description == null || description.isEmpty()) {
            v_description_padding.setVisibility(View.GONE);
            tv_description.setVisibility(View.GONE);
        } else {
            v_description_padding.setVisibility(View.VISIBLE);
            tv_description.setVisibility(View.VISIBLE);
            tv_description.setText(description);
        }
    }

    @Override
    public void showDeliveryDateInView(String date) {
        tv_delivery_date_front.setText(date);
        tv_delivery_date_in_status.setText(date);
    }

    @Override
    public void showProviderInView(String avatar, String name, AddressModel address) {
        loadImageFromUrl(activity(), imv_from_avatar, avatar, true, true);
        tv_from_name.setText(name);
        tv_from_address.setText(getAddress(address));
    }

    @Override
    public void showOrderUserInView(String avatar, String name, AddressModel address) {
        loadImageFromUrl(activity(), imv_to_avatar, avatar, true, true);
        tv_to_name.setText(name);
        tv_to_address.setText(getAddress(address));
    }

    @Override
    public void showStatusInView(String status) {
        imv_status.setVisibility(View.VISIBLE);
        switch (status) {
            case "0":
                imv_status.setImageResource(R.drawable.sts_1);
                break;
            case "1":
                imv_status.setImageResource(R.drawable.sts_2);
                break;
            case "2":
                imv_status.setImageResource(R.drawable.sts_3);
                break;
            case "3":
                imv_status.setImageResource(R.drawable.sts_4);
                break;
            case "4":
                imv_status.setImageResource(R.drawable.sts_5);
                break;
            case "5":
                imv_status.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    @Override
    public void showOrderDateInView(String date) {
        tv_order_date.setText(date);
    }

    @Override
    public void showOrderNumberInView(String order_number) {
        tv_order_number.setText("#" + order_number);
    }

    @Override
    public void onLinkClicked(ProductModel productModel) {
        String link = productModel.getLink();
        if (link == null) {
            return;
        }
        Utils.openDefaultBrowser(activity(), link);
    }

    @Override
    @OnClick(R.id.btn_back)
    public void onBackClicked() {
        orderDetailAcceptedPresenter.navigateToOrderList();
    }

    @Override
    @OnClick(R.id.btn_send_message)
    public void onSendMessageClicked() {
        this.orderDetailAcceptedPresenter.gotoChatScreen();
    }

    @Override
    @OnClick(R.id.btn_receive)
    public void onReceiveClicked() {

    }

    @Override
    public void showProductDetail(ProductModel product) {
        this.orderDetailAcceptedPresenter.showProductDetail(product);
    }

    @Override
    public void showTotalItemsInView(String total) {
        tv_total_items.setText(total);
    }

    @Override
    public void showPriceFeeInView(String price) {
        tv_price.setText(price);
    }

    @Override
    public void showSaleTaxFeeInView(String sale_tax) {
        tv_sales_tax.setText(sale_tax);
    }

    @Override
    public void showServiceFeeInView(String service_fee) {
        tv_service_fee.setText(service_fee);
    }

    @Override
    public void showBuyerFreeInView(String buyer_fee) {
        tv_buy_fee.setText(buyer_fee);
    }

    @Override
    public void showShipFeeInView(String ship_fee) {
        tv_ship_fee.setText(ship_fee);
    }

    @Override
    public void showSurchargeFeeInView(String sub_charge_free) {
        tv_surcharge_fee.setText(sub_charge_free);
    }

    @Override
    public void showOtherFeeInView(String other_fee) {
        tv_other_fee.setText(other_fee);
    }

    @Override
    public void showTotalInView(String total) {
        tv_total_fee.setText(total);
    }

    @Override
    public void showDateTimeSoldInView(String deviverdate) {
        tv_date_time_sold.setText(deviverdate);
    }

    @Override
    public void showDeliveryAddressInView(AddressModel address) {
        tv_address.setText(address.getAddress());
    }

}
