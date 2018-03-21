package com.fernandocejas.android10.sample.presentation.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.fernandocejas.android10.R;

/**
 * Base {@link Dialog} class for every dialog in this application.
 */
public abstract class BaseDialog extends Dialog {

    protected Context mContext;

    protected abstract int getLayoutResId();

    public BaseDialog(Context context) {

        super(context, R.style.AppTheme_Dialog);
        this.mContext = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutResId());
        //
        WindowManager.LayoutParams wmlp = getWindow()
                .getAttributes();
        wmlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wmlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        //
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

}
