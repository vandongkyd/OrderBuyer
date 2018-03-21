package com.fernandocejas.android10.sample.presentation.dialog;

import android.content.Context;
import android.view.WindowManager;
import android.widget.Toast;

import com.fernandocejas.android10.R;
import com.fernandocejas.android10.sample.presentation.view.SelectMediaModeView;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Dialog that shows network error.
 */
public class SelectMediaModeDialog extends BaseDialog implements SelectMediaModeView {

    public interface OnClickListener {

        void onCameraClicked();

        void onGalleryClicked();

        void onDismissClicked();
    }

    private OnClickListener mOnClickListener;

    private int media_type;

    @Override
    protected int getLayoutResId() {
        return R.layout.dialog_select_image_mode;
    }

    public SelectMediaModeDialog(Context context) {
        super(context);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ButterKnife.bind(this);

        setOnDismissListener(dialog -> {
            this.mOnClickListener.onDismissClicked();
        });
        setCanceledOnTouchOutside(true);
    }

    public void setOnClickListener(OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public int getMedia_type() {
        return media_type;
    }

    public void setMedia_type(int media_type) {
        this.media_type = media_type;
    }

    @Override
    @OnClick(R.id.btn_camera)
    public void onCameraClicked() {
        this.mOnClickListener.onCameraClicked();
        //
        dismiss();
    }

    @Override
    @OnClick(R.id.btn_gallery)
    public void onGalleryClicked() {
        this.mOnClickListener.onGalleryClicked();
        //
        dismiss();
    }

    @Override
    @OnClick(R.id.btn_cancel)
    public void onCancelClicked() {
        dismiss();
    }

    public void showError(String message) {
        Toast.makeText(context(), message, Toast.LENGTH_SHORT).show();
    }

    public Context context() {
        return mContext;
    }

}
