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
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.presentation.internal.di.components.OrderComponent;
import com.fernandocejas.android10.order.presentation.presenter.TopWebsitePresenter;
import com.fernandocejas.android10.order.presentation.utils.DialogFactory;
import com.fernandocejas.android10.order.presentation.view.TopWebsiteView;
import com.fernandocejas.android10.sample.presentation.view.fragment.BaseFragment;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 *
 */
public class TopWebsiteFragment extends BaseFragment implements TopWebsiteView {

    @Inject
    TopWebsitePresenter topWebsitePresenter;

    @Bind(R.id.web)
    WebView web;
    @Bind(R.id.edt_web_url)
    EditText edt_web_url;

    private ProgressDialog progressDialog;
    private String url;
    private int request_code;

    public TopWebsiteFragment() {
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
        Bundle arguments = getArguments();
        url = arguments.getString("args_url");
        request_code = arguments.getInt("args_request_code");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_top_website, container, false);
        ButterKnife.bind(this, fragmentView);

        setupWebView();
        setupEditTextSearch();

        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.topWebsitePresenter.setView(this);
        this.topWebsitePresenter.setRequestCode(request_code);
        if (savedInstanceState == null) {
            loadContent(url, null);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.topWebsitePresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.topWebsitePresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.topWebsitePresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.topWebsitePresenter = null;
    }

    @Override
    @OnClick(R.id.btn_back)
    public void onBackClicked() {
        this.topWebsitePresenter.navigateToOrderList();
    }

    @Override
    @OnClick(R.id.fab)
    public void onAddCartClicked() {
        String url = edt_web_url.getText().toString();
        if (url != null && !url.isEmpty()) {
            this.topWebsitePresenter.parseUrlToProduct(url);
        }
    }

    @Override
    public void onSearchAction() {
        String query_search = edt_web_url.getText().toString();
        loadContent(null, query_search);
        // hide virtual keyboard
        InputMethodManager imm = (InputMethodManager) activity().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edt_web_url.getWindowToken(),
                InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    @Override
    @OnClick(R.id.imv_clear)
    public void onClearOnSearchClick() {
        edt_web_url.setText(null);
        edt_web_url.requestFocus();
        // show virtual keyboard
        new Handler().post(mShowImeRunnable);
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

    @Override
    public void loadUrlToWebView(String url) {
        web.loadUrl(url);
    }

    @Override
    @OnClick(R.id.btn_pop_back)
    public void onWebBack() {
        web.goBack();
    }

    @Override
    @OnClick(R.id.btn_pop_next)
    public void onWebNext() {
        web.goForward();
    }

    private void loadContent(String url, String query_search) {
        this.topWebsitePresenter.loadContent(url, query_search);
    }

    private void setupEditTextSearch() {
        edt_web_url.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                onSearchAction();
                return true;
            }
            return false;
        });
    }

    private void setupWebView() {
        web.setWebViewClient(new WebViewClient() {

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                handleUri(Uri.parse(url));
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                /*super.onReceivedSslError(view, handler, error);*/
                // Ignore SSL certificate errors
                handler.proceed();
            }

        });
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setLoadWithOverviewMode(true);
        web.getSettings().setUseWideViewPort(false);
        web.getSettings().setBuiltInZoomControls(false);
    }

    private boolean handleUri(Uri uri) {
        url = uri.toString();
        try {
            edt_web_url.setText(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Based on some condition you need to determine if you are going to load the url
        // in your web view itself or in a browser.
        // You can use `host` or `scheme` or any part of the `uri` to decide.
        if (url.contains("checkout_complete")) {
            // Returning true means that you need to handle what to do with the url
            // e.g. open web page in a Browser
            return true;
        }
        // Returning false means that you are going to load this url in the wv_content itself
        return false;
    }

    private Runnable mShowImeRunnable = () -> {
        InputMethodManager imm = (InputMethodManager) getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm != null) {
            imm.showSoftInput(edt_web_url, 0);
        }
    };

}
