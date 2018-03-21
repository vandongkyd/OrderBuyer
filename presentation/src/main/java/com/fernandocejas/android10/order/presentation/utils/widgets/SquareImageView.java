package com.fernandocejas.android10.order.presentation.utils.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by Windows 10 TIMT on 12-Oct-17.
 */

public class SquareImageView extends android.support.v7.widget.AppCompatImageView {
    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int squareLen = width;
        super.onMeasure(MeasureSpec.makeMeasureSpec(squareLen, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(squareLen, MeasureSpec.EXACTLY));
    }
}
