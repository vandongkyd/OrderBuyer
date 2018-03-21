/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view;

import com.fernandocejas.android10.sample.presentation.view.LoadDataView;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 */
public interface TopWebsiteView extends LoadDataView {

    void onBackClicked();

    void onAddCartClicked();

    void onSearchAction();

    void onClearOnSearchClick();

    void loadUrlToWebView(String url);

    void onWebBack();

    void onWebNext();
}

