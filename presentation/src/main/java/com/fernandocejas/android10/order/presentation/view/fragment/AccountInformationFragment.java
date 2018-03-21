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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.presentation.internal.di.components.OrderComponent;
import com.fernandocejas.android10.order.presentation.model.UserModel;
import com.fernandocejas.android10.order.presentation.presenter.AccountInformationPresenter;
import com.fernandocejas.android10.order.presentation.utils.DialogFactory;
import com.fernandocejas.android10.order.presentation.utils.Utils;
import com.fernandocejas.android10.order.presentation.view.AccountInformationView;
import com.fernandocejas.android10.order.presentation.view.dialog.ChangePasswordDialog;
import com.fernandocejas.android10.sample.presentation.view.fragment.BaseWithMediaPickerFragment;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 *
 */
public class AccountInformationFragment extends BaseWithMediaPickerFragment implements AccountInformationView, Validator.ValidationListener {

    @Inject
    AccountInformationPresenter accountInformationPresenter;

    @Bind(R.id.imv_avatar)
    ImageView imv_avatar;

    @Bind(R.id.edt_name)
    @Pattern(regex = "((\\b[a-zA-Z]{1,40}\\b)\\s*){2,}", messageResId = R.string.sign_up_name_error)
    EditText edt_name;

    @Bind(R.id.tv_email)
    TextView tv_email;

    @Bind(R.id.edt_phone)
    @Pattern(regex = "\\(?([0-9]{3})\\)?([ .-]?)([0-9]{3})\\2([0-9]{4})", messageResId = R.string.sign_up_phone_error)
    EditText edt_phone;

    private Validator validator;
    private ProgressDialog progressDialog;
    private ChangePasswordDialog changePasswordDialog;

    public AccountInformationFragment() {
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
        final View fragmentView = inflater.inflate(R.layout.fragment_account_infomation, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.accountInformationPresenter.setView(this);
        validator = new Validator(this);
        validator.setValidationListener(this);
        if (savedInstanceState == null) {
            this.accountInformationPresenter.loadUser();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.accountInformationPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.accountInformationPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.accountInformationPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.accountInformationPresenter = null;
    }

    @Override
    public void showLoading() {
        if (this.progressDialog == null) {
            this.progressDialog = DialogFactory.createProgressDialog(activity(), R.string.processing);
        }
        this.progressDialog.show();
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
        Toast.makeText(context(), message, Toast.LENGTH_SHORT).show();
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
    public void onValidationSucceeded() {
        String name = edt_name.getText().toString();
        String phone = edt_phone.getText().toString();
        this.accountInformationPresenter.fetchUpdateUserInfo(name, phone);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            EditText editText = (EditText) error.getView();
            editText.setError(error.getCollatedErrorMessage(context()));
        }
    }

    private ChangePasswordDialog.OnClickListener onChangePasswordDialogClickListener = new ChangePasswordDialog.OnClickListener() {

        @Override
        public void onUpdateClicked(String old_password, String password) {
            AccountInformationFragment.this.onUpdatePasswordInDialogClicked(old_password, password);
        }

        @Override
        public void onDismissClicked() {

        }
    };

    @Override
    public void showUserInView(UserModel userModel) {
        //show avatar
        showAvatarInView(userModel.getAvatar());
        //show name
        showUserNameInView(userModel.getName());
        //show email
        showEmailInView(userModel.getEmail());
        //show phone
        showPhoneInView(userModel.getPhone());
    }

    @Override
    public void showAvatarInView(String url) {
        loadImageFromUrl(context(), imv_avatar, url, true, true);
    }

    @Override
    public void showUserNameInView(String name) {
        edt_name.setText(name);
    }

    @Override
    public void showEmailInView(String email) {
        tv_email.setText(email);
    }

    @Override
    public void showPhoneInView(String phone) {
        edt_phone.setText(phone);
    }

    @Override
    @OnClick(R.id.btn_back)
    public void onBackClicked() {
        this.accountInformationPresenter.goBack();
    }

    @Override
    @OnClick(R.id.btn_change_password)
    public void onChangePasswordClicked() {
        if (this.changePasswordDialog == null) {
            this.changePasswordDialog = new ChangePasswordDialog(activity());
            this.changePasswordDialog.setOnClickListener(onChangePasswordDialogClickListener);
        }
        this.changePasswordDialog.show();
    }

    @Override
    @OnClick(R.id.btn_update)
    public void onUpdateClicked() {
        this.validator.validate();
    }

    @Override
    @OnClick(R.id.cmd_avatar)
    public void onAvatarClicked() {
        onPhotoClicked();
    }

    @Override
    public void onUpdatePasswordInDialogClicked(String old_password, String password) {
        this.accountInformationPresenter.fetchChangePassword(old_password, password);
    }

    @Override
    public void showPasswordChangedSuccess() {
        Toast.makeText(context(), getString(R.string.change_password_success), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUpdateUserInfoSuccess() {
        Toast.makeText(context(), getString(R.string.account_info_update_success), Toast.LENGTH_SHORT).show();
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

    public boolean requestPermissions(int requestCode, String[] permissions) {
        if (!Utils.verifyPermissions(getActivity(), permissions)) {
            requestPermissions(
                    permissions,
                    requestCode

            );
            return true;
        }
        return false;
    }

    @Override
    public void onPhotoPicked(String path) {
        AccountInformationFragment.this.accountInformationPresenter.setAvatar(path);
    }

    @Override
    public void onVideoPicked(String path) {

    }

}
