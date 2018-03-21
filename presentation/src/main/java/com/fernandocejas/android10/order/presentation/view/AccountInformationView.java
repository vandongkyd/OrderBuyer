/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.order.presentation.view;

import com.fernandocejas.android10.order.presentation.model.UserModel;
import com.fernandocejas.android10.sample.presentation.view.LoadDataView;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 */
public interface AccountInformationView extends LoadDataView {

    void showUserInView(UserModel userModel);

    void showAvatarInView(String url);

    void showUserNameInView(String name);

    void showEmailInView(String email);

    void showPhoneInView(String phone);

    void onBackClicked();

    void onChangePasswordClicked();

    void onUpdateClicked();

    void onAvatarClicked();

    void onUpdatePasswordInDialogClicked(String old_password, String password);

    void showPasswordChangedSuccess();

    void showUpdateUserInfoSuccess();
}

