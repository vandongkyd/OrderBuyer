/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.presentation.internal.di.components.OrderComponent;
import com.fernandocejas.android10.order.presentation.model.AddressModel;
import com.fernandocejas.android10.order.presentation.model.ImageModel;
import com.fernandocejas.android10.order.presentation.model.OfferModel;
import com.fernandocejas.android10.order.presentation.model.OrderModel;
import com.fernandocejas.android10.order.presentation.model.ProductModel;
import com.fernandocejas.android10.order.presentation.presenter.OrderDetailPresenter;
import com.fernandocejas.android10.order.presentation.utils.Utils;
import com.fernandocejas.android10.order.presentation.view.OrderDetailView;
import com.fernandocejas.android10.order.presentation.view.adapter.OfferAdapter;
import com.fernandocejas.android10.order.presentation.view.adapter.ProductAdapter;
import com.fernandocejas.android10.sample.presentation.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 *
 */
public class OrderDetailFragment extends BaseFragment implements OrderDetailView {

    @Inject
    OrderDetailPresenter orderDetailPresenter;
    @Inject
    OfferAdapter offerAdapter;
    @Inject
    ProductAdapter productAdapter;

    @Bind(R.id.rv_offers)
    RecyclerView rv_offers;
    //    @Bind({R.id.imv_1, R.id.imv_2, R.id.imv_3})
//    ImageView[] imv_product_images;
//    @Bind({R.id.layout_imv_1, R.id.layout_imv_2, R.id.layout_imv_3})
//    FrameLayout[] layout_imv;
//    @Bind(R.id.tv_image_more)
//    TextView tv_image_more;
    @Bind(R.id.v_description_padding)
    View v_description_padding;
    @Bind(R.id.tv_description)
    TextView tv_description;
    @Bind(R.id.tv_from)
    TextView tv_from;
    @Bind(R.id.tv_to)
    TextView tv_to;
    @Bind(R.id.tv_cash)
    TextView tv_cash;
    @Bind(R.id.tv_order_time)
    TextView tv_order_time;
    @Bind(R.id.rl_progress)
    RelativeLayout rl_progress;
    @Bind(R.id.rl_retry)
    RelativeLayout rl_retry;
    @Bind(R.id.lyt_main)
    LinearLayout lyt_main;
    @Bind(R.id.rv_products)
    RecyclerView rv_products;
    @Bind(R.id.tv_order_number)
    TextView tv_order_number;
    @Bind(R.id.tv_offer_title)
    TextView tv_offer_title;

    private String order_id;

    private OfferAdapter.OnItemClickListener onItemOfferClickListener = new OfferAdapter.OnItemClickListener() {
        @Override
        public void onSendMessageClick(OfferModel offerModel) {
            OrderDetailFragment.this.onSendMessageClicked(offerModel);
        }

        @Override
        public void onConfirmClick(OfferModel offerModel) {
            OrderDetailFragment.this.onConfirmClicked(offerModel);
        }
    };

    private ProductAdapter.OnItemClickListener onItemProductClickListener = new ProductAdapter.OnItemClickListener() {

        @Override
        public void onItemClicked(ProductModel productModel) {
            OrderDetailFragment.this.showProductDetail(productModel);
        }

        @Override
        public void onItemRemoved() {

        }

        @Override
        public void onLinkClicked(ProductModel productModel) {
            OrderDetailFragment.this.onLinkClicked(productModel);
        }
    };

    public OrderDetailFragment() {
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
        final View fragmentView = inflater.inflate(R.layout.fragment_order_detail, container, false);
        ButterKnife.bind(this, fragmentView);
        setupRecycleView();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.orderDetailPresenter.setView(this);
        if (savedInstanceState == null) {
            Bundle arguments = getArguments();
            order_id = arguments.getString("args_id");
            loadOrder(order_id);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.orderDetailPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.orderDetailPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.orderDetailPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.orderDetailPresenter = null;
        this.onItemOfferClickListener = null;
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

    private void setupRecycleView() {
        //for offer list
        offerAdapter.setOnItemClickListener(onItemOfferClickListener);
        this.rv_offers.setLayoutManager(new LinearLayoutManager(context()));
        this.rv_offers.setAdapter(offerAdapter);
        ((SimpleItemAnimator) this.rv_offers.getItemAnimator()).setSupportsChangeAnimations(false);
        //for product list
        productAdapter.setOnItemClickListener(onItemProductClickListener);
        this.rv_products.setLayoutManager(new LinearLayoutManager(context()));
        this.rv_products.setAdapter(productAdapter);
        ((SimpleItemAnimator) this.rv_products.getItemAnimator()).setSupportsChangeAnimations(false);

    }

    private String getOrderDate(String order_date) {
        return Utils.getTimeByFormat(order_date);
    }

    private String getPriceWithSymbolCurrency(String amount, String currency) {
        String full_price = "";
        String symbol = Utils.getCurrencySymbol(currency);
        full_price = symbol + "" + Utils.formatDecimal(amount);
        return full_price;
    }

    private List<ImageModel> getImageModelList(Collection<ProductModel> productModelList) {
        List<ImageModel> imageModelList = new ArrayList<>();
        for (ProductModel productModel : productModelList) {
            imageModelList.addAll(productModel.getImages());
        }
        return imageModelList;
    }

//    private String getDescription(Collection<ProductModel> productModelList) {
//        String description = "";
//        for (ProductModel productModel : productModelList) {
//            String name = productModel.getName();
//            if (name != null & !name.isEmpty()) {
//                if (!description.isEmpty()) {
//                    description += "\n";
//                }
//                description += name;
//            }
//        }
//        return description;
//    }

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

    private void loadOrder(String id) {
        this.orderDetailPresenter.loadOrderDetail(id);
    }

//    private void loadImageFromUrl(ImageView view, String url) {
//        Glide.with(activity())
//                .load(url)
//                .into(view);
//    }

    @OnClick(R.id.bt_retry)
    void onButtonRetryClick() {
        OrderDetailFragment.this.loadOrder(order_id);
    }

    @Override
    public void showOrderDetailInView(OrderModel orderModel) {

        this.productAdapter.setCurrency_default(orderModel.getCurrency());

        //show product list
        Collection<ProductModel> productModels = orderModel.getProducts();
        if (productModels != null && !productModels.isEmpty()) {
            renderProductList(productModels);
        }
        //show offer list
        Collection<OfferModel> offerModels = orderModel.getOffers();
        if (offerModels != null && !offerModels.isEmpty()) {
            offerAdapter.setCurrency(orderModel.getCurrency());
        }
        renderOfferList(offerModels);
        //show image list
//        Collection<ImageModel> imageModels = getImageModelList(orderModel.getProducts());
//        renderImageList(imageModels);
        //show description
//        String description = getDescription(orderModel.getProducts());
        String description = orderModel.getDescription();
        showDescriptionInView(description);
        //show shipping info
        String deliveryFrom = orderModel.getCountry_name();
        String deliveryTo = getDeliveryTo(orderModel.getAddressModel());
        showShippingInfoInView(deliveryFrom, deliveryTo);
        //show price
        String full_price = getPriceWithSymbolCurrency(orderModel.getAmount(), orderModel.getCurrency());
        showPriceInView(full_price);
        //show order date
        String order_date = getOrderDate(orderModel.getCreated_at());
        showOrderDateInView(order_date);
        //show order number
        String order_number = orderModel.getId();
        showOrderNumberInView(order_number);

        //
        lyt_main.setVisibility(View.VISIBLE);
    }

    @Override
    public void renderProductList(Collection<ProductModel> productModels) {
        productAdapter.setProductsCollection(productModels);
    }

    @Override
    public void renderOfferList(Collection<OfferModel> offerModels) {
        if (offerModels == null || offerModels.isEmpty()) {
            tv_offer_title.setVisibility(View.GONE);
        } else {
            tv_offer_title.setVisibility(View.VISIBLE);
            offerAdapter.setOffersCollection(offerModels);
        }
    }

    @Override
    public void renderImageList(Collection<ImageModel> imageModels) {

//        int listImageSize = imageModels.size();
//
//        if (listImageSize <= 3) {
//            if (listImageSize <= 0) {
//                v_description_padding.setVisibility(View.GONE);
//            } else {
//                v_description_padding.setVisibility(View.VISIBLE);
//            }
//            //hide show more
//            tv_image_more.setVisibility(View.GONE);
//
//            for (int i = 0; i < imv_product_images.length; i++) {
//                if (i >= listImageSize) {
//                    //we have no more image to show
//                    layout_imv[i].setVisibility(View.GONE);
//                } else {
//                    layout_imv[i].setVisibility(View.VISIBLE);
//                    //
//                    String image_url = null;
//                    try {
//                        image_url = ((List<ImageModel>) imageModels).get(i).getImage();
//                    } catch (Exception ex) {
//                    }
//                    loadImageFromUrl(imv_product_images[i], image_url);
//                }
//            }
//        } else {
//
//            //reset view
//            for (int i = 0; i < layout_imv.length; i++) {
//                layout_imv[i].setVisibility(View.VISIBLE);
//            }
//
//            //show show more
//            tv_image_more.setVisibility(View.VISIBLE);
//            tv_image_more.setText("+" + (listImageSize - 2));
//
//            //get image
//            String image_url_1, image_url_2, image_url_3;
//            image_url_1 = ((List<ImageModel>) imageModels).get(0).getImage();
//            image_url_2 = ((List<ImageModel>) imageModels).get(1).getImage();
//            image_url_3 = ((List<ImageModel>) imageModels).get(2).getImage();
//            loadImageFromUrl(imv_product_images[0], image_url_1);
//            loadImageFromUrl(imv_product_images[1], image_url_2);
//            loadImageFromUrl(imv_product_images[2], image_url_3);
//        }
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
    public void showShippingInfoInView(String deliveryFrom, String deliveryTo) {
        tv_from.setText(deliveryFrom);
        tv_to.setText(deliveryTo);
    }

    @Override
    public void showPriceInView(String price) {
        tv_cash.setText(price);
    }

    @Override
    public void showOrderDateInView(String date) {
        tv_order_time.setText(date);
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
    public void onSendMessageClicked(OfferModel offerModel) {
        this.orderDetailPresenter.setProvider(offerModel);
        this.orderDetailPresenter.gotoChatScreen();
    }

    @Override
    public void onConfirmClicked(OfferModel offerModel) {
        this.orderDetailPresenter.gotoAcceptedScreen(offerModel);
    }

    @Override
    @OnClick(R.id.btn_back)
    public void onBackClicked() {
        orderDetailPresenter.navigateToOrderList();
    }

    @Override
    public void showProductDetail(ProductModel product) {
        this.orderDetailPresenter.showProductDetail(product);
    }

}
