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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.domain.Address;
import com.fernandocejas.android10.order.domain.SettingByCountry;
import com.fernandocejas.android10.order.presentation.internal.di.components.OrderComponent;
import com.fernandocejas.android10.order.presentation.model.AddressModel;
import com.fernandocejas.android10.order.presentation.model.ProductModel;
import com.fernandocejas.android10.order.presentation.presenter.OrderPresenter;
import com.fernandocejas.android10.order.presentation.utils.Constants;
import com.fernandocejas.android10.order.presentation.utils.DialogFactory;
import com.fernandocejas.android10.order.presentation.utils.Utils;
import com.fernandocejas.android10.order.presentation.view.OrderView;
import com.fernandocejas.android10.order.presentation.view.adapter.AddressSpinnerAdapter;
import com.fernandocejas.android10.order.presentation.view.adapter.ItemTouchHelperCallback;
import com.fernandocejas.android10.order.presentation.view.adapter.ProductAdapter;
import com.fernandocejas.android10.sample.presentation.view.fragment.BaseFragment;
import com.loopeer.itemtouchhelperextension.ItemTouchHelperExtension;

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
public class OrderFragment extends BaseFragment implements OrderView {

    @Inject
    OrderPresenter orderPresenter;

    @Inject
    ProductAdapter productAdapter;
    @Inject
    AddressSpinnerAdapter addressSpinnerAdapter;

    @Bind(R.id.rv_products)
    RecyclerView rv_products;
    @Bind(R.id.tv_submit_info)
    TextView tv_submit_info;
    @Bind(R.id.tv_total_items)
    TextView tv_total_items;
    @Bind(R.id.tv_price)
    TextView tv_price;
    @Bind(R.id.tv_sales_tax)
    TextView tv_sales_tax;
    @Bind(R.id.tv_total)
    TextView tv_total;
    @Bind(R.id.tv_address_)
    TextView tv_address_;
    @Bind(R.id.edt_description)
    EditText edt_description;
    @Bind(R.id.spn_address)
    Spinner spn_address;
    @Bind(R.id.tv_sales_tax_title)
    TextView tv_sales_tax_title;

    private ProgressDialog progressDialog;

    private ProductAdapter.OnItemClickListener onProductItemClickListener = new ProductAdapter.OnItemClickListener() {
        @Override
        public void onItemClicked(ProductModel productModel) {
            if (OrderFragment.this.orderPresenter != null && productModel != null) {

            }
        }

        @Override
        public void onItemRemoved() {
            if (Constants.PRODUCTS.isEmpty()) {
                OrderFragment.this.orderPresenter.navigationToProduct();
                return;
            }
            OrderFragment.this.orderPresenter.calculatePrice();
        }

        @Override
        public void onLinkClicked(ProductModel productModel) {
            OrderFragment.this.onLinkClicked(productModel);
        }
    };

    public OrderFragment() {
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
        final View fragmentView = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, fragmentView);
        setupRecyclerView();
        setupSpinnerView();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.orderPresenter.setView(this);
        if (savedInstanceState == null) {
            Bundle arguments = getArguments();
            SettingByCountry setting = (SettingByCountry) arguments.getSerializable("args_setting");
            if (setting != null) {
                this.orderPresenter.setSettingByCountry(setting);
            }
            this.orderPresenter.loadProducts();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.orderPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.orderPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.orderPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.orderPresenter = null;
    }

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

    private void setupRecyclerView() {
        this.productAdapter.setOnItemClickListener(onProductItemClickListener);
        this.rv_products.setLayoutManager(new LinearLayoutManager(context()));
        this.rv_products.setAdapter(productAdapter);
        this.initSwipe();
    }

    private AdapterView.OnItemSelectedListener onAddressOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            AddressModel address = addressSpinnerAdapter.getItem(position);
            if (address.isIs_header()) {
                OrderFragment.this.orderPresenter.gotToAddAddress();
            } else {
                OrderFragment.this.orderPresenter.setAddress(address);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    private void setupSpinnerView() {
        this.spn_address.setAdapter(addressSpinnerAdapter);
        this.spn_address.setOnItemSelectedListener(onAddressOnItemSelectedListener);
    }

    @Override
    public void renderProducts(List<ProductModel> productModelList) {
        this.productAdapter.setProductsCollection(productModelList);
    }

    @Override
    @OnClick(R.id.cmd_submit)
    public void onSubmitClicked() {
        this.orderPresenter.submitOrder(edt_description.getText().toString());
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
    public void showTotalItemsInView(String total_items) {
        tv_total_items.setText(total_items);
    }

    @Override
    public void showTotalInView(String total) {
        tv_total.setText(total);
    }

    @Override
    public void showPriceInView(String price) {
        tv_price.setText(price);
    }

    @Override
    public void showSalesTaxInView(String tax) {
        tv_sales_tax.setText(tax);
    }

    @Override
    public void showAddressInView(String address) {
        tv_address_.setText(address);
    }

    @Override
    public void showSubmitInformation(int quantity, String currencySymbol, String money) {
        tv_submit_info.setText(activity().getString(R.string.order_submit_information, quantity + "", currencySymbol, money + ""));
    }

    @Override
    public void showSalesTaxTitleInView(String tax) {
        tv_sales_tax_title.setText(getString(R.string.order_sales_tax) + " - " + tax + "%");
    }

    private void initSwipe() {
        ItemTouchHelperCallback callback = new ItemTouchHelperCallback();
        ItemTouchHelperExtension itemTouchHelper = new ItemTouchHelperExtension(callback);
        itemTouchHelper.attachToRecyclerView(rv_products);
        productAdapter.setItemTouchHelperExtension(itemTouchHelper);
    }

    @Override
    @OnClick(R.id.btn_back)
    public void onBackClicked() {
        this.orderPresenter.navigationToProduct();
    }

    @Override
    public void showSubmitSuccess() {
        Toast.makeText(activity(), activity().getString(R.string.order_submit_success), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void renderAddresses(Collection<AddressModel> addresses) {

        Collection<AddressModel> addressModels = new ArrayList<>();

        //add header
        AddressModel address = new AddressModel();
        address.setIs_header(true);
        addressModels.add(address);
        //
        if (addresses != null && !addresses.isEmpty()) {
            addressModels.addAll(addresses);
        }
        addressSpinnerAdapter.replaceWith(addressModels);
        selectAddressInView(1);
    }

    @Override
    @OnClick(R.id.cmd_address)
    public void onChoiceAddressClicked() {
        spn_address.performClick();
    }

    @Override
    public void onAddressAdded(Address address) {
        this.orderPresenter.addAddress(address);
    }

    @Override
    public void selectAddressInView(int position) {
        try {
            this.spn_address.setSelection(position);
        } catch (Exception ex) {
        }
    }
}
