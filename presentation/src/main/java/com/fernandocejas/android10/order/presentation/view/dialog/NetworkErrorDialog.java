package com.fernandocejas.android10.order.presentation.view.dialog;

import android.content.Context;
import android.view.WindowManager;
import android.widget.Button;

import com.fernandocejas.android10.R;
import com.fernandocejas.android10.sample.presentation.dialog.BaseDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Dialog that shows network error.
 */
public class NetworkErrorDialog extends BaseDialog {

    public interface OnClickListener {

        void onRetryClicked();

        void onDismissClicked();
    }

    @Bind(R.id.bt_retry)
    Button bt_retry;

    private OnClickListener mOnClickListener;

    @Override
    protected int getLayoutResId() {

        return R.layout.dialog_network_error;
    }

    public NetworkErrorDialog(Context context, OnClickListener mOnClickListener) {

        super(context);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ButterKnife.bind(this);

        this.mOnClickListener = mOnClickListener;

        setOnDismissListener(dialog -> {
            this.mOnClickListener.onDismissClicked();
        });
    }

    @OnClick(R.id.bt_retry)
    void onButtonRetryClick() {
        this.mOnClickListener.onRetryClicked();
    }
}
