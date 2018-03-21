/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.domain.Product;
import com.fernandocejas.android10.order.presentation.internal.di.components.OrderComponent;
import com.fernandocejas.android10.order.presentation.model.ProductModel;
import com.fernandocejas.android10.order.presentation.presenter.ProductListPresenter;
import com.fernandocejas.android10.order.presentation.utils.DialogFactory;
import com.fernandocejas.android10.order.presentation.utils.Utils;
import com.fernandocejas.android10.order.presentation.view.ProductListView;
import com.fernandocejas.android10.order.presentation.view.adapter.ProductDetailAdapter;
import com.fernandocejas.android10.order.presentation.view.dialog.AddUrlDialog;
import com.fernandocejas.android10.order.presentation.view.dialog.SlideshowDialog;
import com.fernandocejas.android10.sample.presentation.view.fragment.BaseFragment;
import com.fernandocejas.android10.sample.presentation.view.fragment.BaseWithMediaPickerFragment;
import java.util.Collection;
import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 *
 *
 */
public class ProductListFragment extends BaseWithMediaPickerFragment implements ProductListView {

    @Inject
    ProductListPresenter productListPresenter;
    @Inject
    ProductDetailAdapter productDetailAdapter;

    @Bind(R.id.rv_products)
    RecyclerView rv_products;

    private ProgressDialog progressDialog;
    private SlideshowDialog slideshowDialog;
    private AddUrlDialog mAddUrlDialog;

    private ProductDetailAdapter.OnItemClickListener onItemClickListener = new ProductDetailAdapter.OnItemClickListener() {
        @Override
        public void onMinusQuantity(ProductModel productModel, int position) {
            productListPresenter.changeQuantity(productModel.getLink(), -1, 1000, position);
        }

        @Override
        public void onPlusQuantity(ProductModel productModel, int position) {
            productListPresenter.changeQuantity(productModel.getLink(), 1, 1000, position);
        }

        @Override
        public void onWeightChange(ProductModel productModel, String s) {
            productListPresenter.changeWeight(productModel.getLink(), s);
        }

        @Override
        public void onPriceChange(ProductModel productModel, String s) {
            productListPresenter.changePrice(productModel.getLink(), s);
        }

        @Override
        public void onLinkClicked(ProductModel productModel) {
            String link = productModel.getLink();
            if (link == null) {
                return;
            }
            Utils.openDefaultBrowser(activity(), productModel.getLink());
        }

        @Override
        public void onPickPhotoClicked(ProductModel productModel, int position) {
            productListPresenter.setCurrentPosition(position);
            productListPresenter.setCurrentLink(productModel.getLink());
            ProductListFragment.this.onPhotoClicked();
        }

        @Override
        public void onShowSlideshowClicked(ProductModel productModel) {
            ProductListFragment.this.onShowSlideshowClicked(productModel);
        }

    };

    public ProductListFragment() {
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
        final View fragmentView = inflater.inflate(R.layout.fragment_product_list, container, false);
        ButterKnife.bind(this, fragmentView);
        setupRecyclerView();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.productListPresenter.setView(this);
        if (savedInstanceState == null) {
            Bundle arguments = getArguments();
            Product product = (Product) arguments.getSerializable("args_product");
            if (product != null) {
                this.productListPresenter.setProduct(product);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == productListPresenter.REQUEST_ADD_PRODUCT_FROM_URL) {
            if (resultCode == RESULT_OK) {
                Product product = (Product) data.getSerializableExtra("return_product");
                if (product != null) {
                    this.productListPresenter.setProduct(product);
                }
            }
        }
    }

    @Override
    public void onPhotoPicked(String path) {
        this.productListPresenter.addPhotoToProduct(path);
        this.productDetailAdapter.notifyItemChanged(this.productListPresenter.getCurrentPosition());
    }

    @Override
    public void onVideoPicked(String path) {

    }

    @Override
    public void onResume() {
        super.onResume();
        //this.productListPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.productListPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.productListPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.productListPresenter = null;
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

    private void setupRecyclerView() {
        productDetailAdapter.setOnItemClickListener(onItemClickListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context());
        this.rv_products.setLayoutManager(linearLayoutManager);
        this.rv_products.setAdapter(productDetailAdapter);
        ((SimpleItemAnimator) this.rv_products.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }

    @Override
    public Activity activity() {
        return getActivity();
    }

    @Override
    @OnClick(R.id.btn_more)
    public void onAddMoreClicked() {
        showAddUrlDialog();
    }

    @Override
    @OnClick(R.id.btn_next)
    public void onNextClicked() {
        this.productListPresenter.goToOrder();
    }

    @Override
    @OnClick(R.id.btn_back)
    public void onBackClicked() {
        productListPresenter.navigateToOrderList();
    }

    @Override
    public void showAddUrlDialog() {
        if (mAddUrlDialog == null) {
            mAddUrlDialog = new AddUrlDialog(activity(), new AddUrlDialog.OnClickListener() {
                @Override
                public void onAddClicked(String url) {
                    productListPresenter.addUrl(ProductListFragment.this, url);
                }

                @Override
                public void onDismissClicked() {

                }
            });
        }
        if (!mAddUrlDialog.isShowing()) {
            mAddUrlDialog.show();
        }
    }

    @Override
    public void onShowSlideshowClicked(ProductModel productModel) {
        Collection imageModels = productModel.getImages();
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
    public void renderProductList(Collection<ProductModel> productCollection) {
        if (productCollection != null) {
            this.productDetailAdapter.setProductsCollection(productCollection);
        }
    }

    @Override
    public void reRenderProductInList(ProductModel productModel, int position) {
        if (productModel != null) {
            this.productDetailAdapter.setProduct(productModel, position);
        }
    }
}
