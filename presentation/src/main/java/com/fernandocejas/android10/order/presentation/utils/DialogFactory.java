package com.fernandocejas.android10.order.presentation.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.StringRes;

public final class DialogFactory {

    public static ProgressDialog createProgressDialog(Context context, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        return progressDialog;
    }

    public static ProgressDialog createProgressDialog(Context context,
                                                      @StringRes int messageResource) {
        return createProgressDialog(context, context.getString(messageResource));
    }

}
