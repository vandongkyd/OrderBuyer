/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view;

import com.fernandocejas.android10.sample.presentation.view.LoadDataView;
import com.mobsandgeeks.saripaar.ValidationError;

import java.util.List;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 */
public interface SignUpView extends LoadDataView{

    void onSignUpClicked();

    void signUp();

    void onValidateError(List<ValidationError> errors);

    void resetValidate();

    void onSignInClicked();

    void onLayoutNameClicked();

    void onLayoutEmailClicked();

    void onLayoutPasswordClicked();

    void onLayoutPasswordConfirmClicked();
}

