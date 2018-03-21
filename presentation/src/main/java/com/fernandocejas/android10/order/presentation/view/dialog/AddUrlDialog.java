package com.fernandocejas.android10.order.presentation.view.dialog;

import android.content.Context;
import android.view.WindowManager;
import android.widget.EditText;

import com.fernandocejas.android10.R;
import com.fernandocejas.android10.sample.presentation.dialog.BaseDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Dialog that shows network error.
 */
public class AddUrlDialog extends BaseDialog {

    @Bind(R.id.tv_url)
    EditText tv_url;

    public interface OnClickListener {

        void onAddClicked(String url);

        void onDismissClicked();
    }

    private OnClickListener mOnClickListener;

    @Override
    protected int getLayoutResId() {
        return R.layout.dialog_add_url;
    }

    public AddUrlDialog(Context context, OnClickListener mOnClickListener) {
        super(context);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ButterKnife.bind(this);

        this.mOnClickListener = mOnClickListener;
        setOnDismissListener(dialog -> {
            this.mOnClickListener.onDismissClicked();
        });
        setCanceledOnTouchOutside(true);
    }

    @OnClick(R.id.btn_add)
    void onAddClicked() {
        this.mOnClickListener.onAddClicked(tv_url.getText().toString());
        dismiss();
    }
}
