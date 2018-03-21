/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.AppCompatImageButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.domain.Product;
import com.fernandocejas.android10.order.presentation.internal.di.components.OrderComponent;
import com.fernandocejas.android10.order.presentation.model.ImageModel;
import com.fernandocejas.android10.order.presentation.model.ProductModel;
import com.fernandocejas.android10.order.presentation.presenter.ProductDetailPresenter;
import com.fernandocejas.android10.order.presentation.utils.DialogFactory;
import com.fernandocejas.android10.order.presentation.utils.Utils;
import com.fernandocejas.android10.order.presentation.view.ProductDetailView;
import com.fernandocejas.android10.order.presentation.view.adapter.ProductDetailAdapter;
import com.fernandocejas.android10.order.presentation.view.dialog.SlideshowDialog;
import com.fernandocejas.android10.sample.presentation.view.fragment.BaseFragment;

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
public class ProductDetailFragment extends BaseFragment implements ProductDetailView {

    @Inject
    ProductDetailPresenter productDetailPresenter;

    @Bind(R.id.tv_name)
    TextView tv_name;
    @Bind(R.id.tv_description)
    TextView tv_description;
    @Bind(R.id.btn_link)
    TextView btn_link;
    @Bind(R.id.tv_quantity)
    TextView tv_quantity;
    @Bind(R.id.edt_weight)
    EditText edt_weight;
    @Bind(R.id.edt_price)
    EditText edt_price;
    @Bind({R.id.layout_imv_1, R.id.layout_imv_2, R.id.layout_imv_3})
    FrameLayout[] layout_imv;
    @Bind({R.id.imv_1, R.id.imv_2, R.id.imv_3})
    ImageView[] imv_product_images;
    @Bind(R.id.btn_remove_quantity)
    AppCompatImageButton btn_remove_quantity;
    @Bind(R.id.btn_add_quantity)
    AppCompatImageButton btn_add_quantity;
    @Bind(R.id.tv_unit)
    TextView tv_unit;
    @Bind(R.id.tv_currency)
    TextView tv_currency;
    @Bind(R.id.v_description_padding)
    View v_description_padding;
    @Bind(R.id.tv_image_more)
    TextView tv_image_more;
    @Bind(R.id.tv_order_number)
    TextView tv_order_number;

    private ProgressDialog progressDialog;
    private SlideshowDialog slideshowDialog;

    public ProductDetailFragment() {
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
        final View fragmentView = inflater.inflate(R.layout.fragment_product_detail, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.productDetailPresenter.setView(this);
        if (savedInstanceState == null) {
            Bundle arguments = getArguments();
            Product product = (Product) arguments.getSerializable("args_product");
            if (product != null) {
                this.productDetailPresenter.setProduct(product);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.productDetailPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.productDetailPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.productDetailPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.productDetailPresenter = null;
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

    private String getCurrencySymbol(String currency) {
        return Utils.getCurrencySymbol(currency);
    }

    private float getWeightAmount(String weight, String quantity) {
        float w = 0;
        try {
            w = Float.valueOf(weight) * Integer.valueOf(quantity);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return w;
    }

    private float getPriceAmount(String price, String quantity) {
        float p = 0;
        try {
            p = Float.valueOf(price) * Integer.valueOf(quantity);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return p;
    }

    private String getCommonLink(String link) {
        try {
            String l = link.replace("https://", "");
            l = l.split("/")[0];
            return l;
        } catch (Exception ex) {
        }
        return link;
    }

    @Override
    public void renderImageList(ProductDetailAdapter.ProductViewHolder viewHolder, Collection<ImageModel> imageModels) {
        int listImageSize = imageModels.size();

        if (listImageSize <= 3) {
            if (listImageSize <= 0) {
                v_description_padding.setVisibility(View.GONE);
            } else {
                v_description_padding.setVisibility(View.VISIBLE);
            }
            //hide show more
            tv_image_more.setVisibility(View.GONE);

            for (int i = 0; i < imv_product_images.length; i++) {
                if (i >= listImageSize) {
                    //we have no more image to show
                    layout_imv[i].setVisibility(View.GONE);
                } else {
                    layout_imv[i].setVisibility(View.VISIBLE);
                    //
                    String image_url = null;
                    try {
                        image_url = ((List<ImageModel>) imageModels).get(i).getImage();
                    } catch (Exception ex) {
                    }
                    loadImageFromUrl(context(), imv_product_images[i], image_url, false, false);
                }
            }
        } else {

            //reset view
            for (int i = 0; i < layout_imv.length; i++) {
                layout_imv[i].setVisibility(View.VISIBLE);
            }

            //show show more
            tv_image_more.setVisibility(View.VISIBLE);
            tv_image_more.setText("+" + (listImageSize - 2));

            //get image
            String image_url_1, image_url_2, image_url_3;
            image_url_1 = ((List<ImageModel>) imageModels).get(0).getImage();
            image_url_2 = ((List<ImageModel>) imageModels).get(1).getImage();
            image_url_3 = ((List<ImageModel>) imageModels).get(2).getImage();
            loadImageFromUrl(context(), imv_product_images[0], image_url_1, false, false);
            loadImageFromUrl(context(), imv_product_images[1], image_url_2, false, false);
            loadImageFromUrl(context(), imv_product_images[2], image_url_3, false, false);
        }
    }

    @Override
    public void showNameProductInView(ProductDetailAdapter.ProductViewHolder viewHolder, String name) {
        tv_name.setText(name);
    }

    @Override
    public void showDescriptionInView(ProductDetailAdapter.ProductViewHolder viewHolder, String description) {
        tv_description.setText(Utils.fromHtml(description));
    }

    @Override
    public void showLinkInView(ProductDetailAdapter.ProductViewHolder viewHolder, String link) {
        btn_link.setText(link);
    }

    @Override
    public void showQuantityInView(ProductDetailAdapter.ProductViewHolder viewHolder, String quantity, int stock) {
        tv_quantity.setText(quantity);
        int q = Integer.valueOf(quantity);
        //less than 1
        if (q <= 1) {
            btn_remove_quantity.setImageResource(R.drawable.ic_minus_disable);
            btn_remove_quantity.setEnabled(false);
        } else {
            btn_remove_quantity.setImageResource(R.drawable.ic_minus_enable);
            btn_remove_quantity.setEnabled(true);
        }
        //more than stock
        if (q >= stock) {
            btn_add_quantity.setImageResource(R.drawable.ic_plus_disable);
            btn_add_quantity.setEnabled(false);
        } else {
            btn_add_quantity.setImageResource(R.drawable.ic_plus_enable);
            btn_add_quantity.setEnabled(true);
        }
    }

    @Override
    public void showWeightInView(ProductDetailAdapter.ProductViewHolder viewHolder, String weight) {
        edt_weight.setText(weight);
        edt_weight.setEnabled(false);
    }

    @Override
    public void showPriceInView(ProductDetailAdapter.ProductViewHolder viewHolder, String price) {
        edt_price.setText(price);
        edt_price.setEnabled(false);
    }

    @Override
    public void showWeightUnitInView(ProductDetailAdapter.ProductViewHolder viewHolder, String unit) {
        tv_unit.setText(unit);
    }

    @Override
    public void showCurrencySymbolInView(ProductDetailAdapter.ProductViewHolder viewHolder, String symbol) {
        tv_currency.setText(symbol);
    }

    @Override
    public void onPlusQuantityClicked(ProductModel productModel, int position) {

    }

    @Override
    public void onMinusQuantityClicked(ProductModel productModel, int position) {

    }

    @Override
    public void onWeightInputChanged(ProductDetailAdapter.ProductViewHolder viewHolder, ProductModel productModel) {

    }

    @Override
    public void onPriceInputChanged(ProductDetailAdapter.ProductViewHolder viewHolder, ProductModel productModel) {

    }

    @Override
    public void onLinkClicked(ProductModel productModel) {

    }

    @Override
    public void onPickPhotoClicked(ProductModel productModel, int position) {

    }

    @Override
    public void onShowSlideshowClicked(ProductModel productModel) {

    }

    @Override
    public void showOrderNumberInView(String order_number) {
        tv_order_number.setText("#" + order_number);
    }

    @Override
    public void showProductInView(ProductModel productModel) {
        //show name
        showNameProductInView(null, productModel.getName());
        //show image list
        renderImageList(null, productModel.getImages());
        //show description
        showDescriptionInView(null, productModel.getDescription());
        //show link
        String link = getCommonLink(productModel.getLink());
        showLinkInView(null, link);
        //show weight
        showWeightInView(null, productModel.getWeight());
        //show price
        showPriceInView(null, productModel.getPrice());
        //show weight unit
        showWeightUnitInView(null, productModel.getWeight_unit());
        //show currency symbol
        String currency_symbol = getCurrencySymbol(productModel.getCurrency());
        showCurrencySymbolInView(null, currency_symbol);
        //show quantity
        showQuantityInView(null, productModel.getQuantity(), 1000);
        //show order number
        showOrderNumberInView(productModel.getOrderid());
    }

    @Override
    @OnClick(R.id.btn_back)
    public void onBackClicked() {
        this.productDetailPresenter.goBack();
    }

    @Override
    @OnClick(R.id.cmd_show_image)
    public void onShowSlideshowClicked() {
        Collection imageModels = this.productDetailPresenter.getImageModeList();
        if (imageModels == null || imageModels.isEmpty()) {
            return;
        }
        if (slideshowDialog == null) {
            slideshowDialog = new SlideshowDialog(activity());
        }
        slideshowDialog.setImageModels(imageModels);
        slideshowDialog.show();
    }

    @Override
    @OnClick(R.id.btn_link)
    public void onLinkClicked() {
        String link = this.productDetailPresenter.getLink();
        if (link == null) {
            return;
        }
        Utils.openDefaultBrowser(activity(), link);
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
}
