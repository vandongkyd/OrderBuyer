/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.view;

import com.fernandocejas.android10.sample.presentation.view.LoadDataView;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 */
public interface SelectMediaModeView {

    void onCameraClicked();

    void onGalleryClicked();

    void onCancelClicked();

}

