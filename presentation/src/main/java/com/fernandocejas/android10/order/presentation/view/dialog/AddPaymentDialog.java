package com.fernandocejas.android10.order.presentation.view.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;
import android.widget.Toast;

import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.presentation.utils.widgets.CardInputWidget;
import com.fernandocejas.android10.order.presentation.view.AddPaymentView;
import com.fernandocejas.android10.sample.presentation.dialog.BaseDialog;
import com.stripe.android.model.Card;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 *
 */
public class AddPaymentDialog extends BaseDialog implements AddPaymentView {

    @Bind(R.id.card_input_widget)
    CardInputWidget card_input_widget;

    public interface OnClickListener {

        void onSaveClicked(String card_number, int exp_month, int exp_year, String cvc);

        void onDismissClicked();
    }

    private OnClickListener onClickListener;

    @Override
    protected int getLayoutResId() {
        return R.layout.dialog_add_payment;
    }

    public AddPaymentDialog(Context context) {
        super(context);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ButterKnife.bind(this);
        setOnDismissListener(dialog -> {
            this.onClickListener.onDismissClicked();
        });
        setCanceledOnTouchOutside(true);
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
    public void showCardNumberInView(String card_number) {
        card_input_widget.setCardNumber(card_number);
    }

    @Override
    public void showExpDateInView(int exp_month, int exp_year) {
        card_input_widget.setExpiryDate(exp_month, exp_year);
    }

    @Override
    public void showCVVInView(String cvv) {
        card_input_widget.setCvcCode(cvv);
    }

    @Override
    @OnClick(R.id.btn_save)
    public void onSaveClicked() {
        Card cardToSave = card_input_widget.getCard();
        if (cardToSave == null) {
            showError(context().getString(R.string.add_payment_card_invalid));
            return;
        }
        String card_number = cardToSave.getNumber();
        int exp_month = cardToSave.getExpMonth();
        int exp_year = cardToSave.getExpYear();
        String cvc = cardToSave.getCVC();
        //
        this.onClickListener.onSaveClicked(card_number, exp_month, exp_year, cvc);
        //
        dismiss();
    }

    @Override
    public void show() {
        super.show();
        resetInView();
    }

    private void resetInView() {
        showCardNumberInView(null);
        showCVVInView(null);
    }

}
