package com.fernandocejas.android10.order.presentation.view.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.presentation.view.ChangePasswordView;
import com.fernandocejas.android10.sample.presentation.dialog.BaseDialog;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 *
 */
public class ChangePasswordDialog extends BaseDialog implements ChangePasswordView, Validator.ValidationListener {

    @NotEmpty
    @Bind(R.id.edt_old_password)
    EditText edt_old_password;

    @Password
    @Bind(R.id.edt_password)
    EditText edt_password;

    @ConfirmPassword
    @Bind(R.id.edt_password_confirm)
    EditText edt_password_confirm;

    public interface OnClickListener {

        void onUpdateClicked(String old_password, String password);

        void onDismissClicked();
    }

    private OnClickListener onClickListener;
    private Validator validator;

    @Override
    protected int getLayoutResId() {
        return R.layout.dialog_change_password;
    }

    public ChangePasswordDialog(Context context) {
        super(context);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ButterKnife.bind(this);
        setOnDismissListener(dialog -> {
            this.onClickListener.onDismissClicked();
        });
        setCanceledOnTouchOutside(true);
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
        return mContext;
    }

    @Override
    public Activity activity() {
        return null;
    }

    @Override
    public void showOldPasswordInView(String password) {
        edt_old_password.setText(password);
    }

    @Override
    public void showNewPasswordInView(String password) {
        edt_password.setText(password);
    }

    @Override
    public void showConfirmPasswordInView(String password) {
        edt_password_confirm.setText(password);
    }

    @Override
    @OnClick(R.id.btn_update)
    public void onUpdateClicked() {
        validator.validate();
    }


    @Override
    public void onValidationSucceeded() {
        String old_password = edt_old_password.getText().toString();
        String password = edt_password.getText().toString();
        this.onClickListener.onUpdateClicked(old_password, password);
        //
        dismiss();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            EditText editText = (EditText) error.getView();
            editText.setError(error.getCollatedErrorMessage(context()));
        }
    }

    @Override
    public void show() {
        super.show();
        resetInView();
    }

    private void resetInView() {
        showOldPasswordInView(null);
        showNewPasswordInView(null);
        showConfirmPasswordInView(null);
    }

}
