package com.fernandocejas.android10.order.presentation.utils.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.fernandocejas.android10.R;

/**
 * Created by jerry on 11/13/2015.
 */
public class RatingWidget extends View {
    private int mDrawBitmapWidth = 300; //!< Default width of view
    private int mDrawBitmapHeight = 60; //!< Default height of view
    private Bitmap mResultBitmap; //!< Bitmap to draw markers on
    private Canvas mResultCanvas; //!< Canvas to draw markers to (to draw to bitmap)
    private Paint mDrawingPaint; //!< Paint for drawing markers
    private Rect mDrawRect; //!< Draw rect for drawing completed wheel to view canvas (needs to be set to size of view)
    private float density;
    private int percent;

    public RatingWidget(Context context) {
        super(context);
        init(context);
    }

    public RatingWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void setValue(int percent) {
        this.percent = percent;
    }

    /*
     * Instantiate members, avoid instantiating anything in onDraw or onMeasure methods
     */
    Bitmap bitmap;
    Bitmap bitmapfull;

    private void init(Context context) {
        density = context.getResources().getDisplayMetrics().density;
        mDrawBitmapWidth = getDensity(mDrawBitmapWidth);
        mDrawBitmapHeight = getDensity(mDrawBitmapHeight);
        //
        mDrawingPaint = new Paint();
        mDrawRect = new Rect(0, 0, 0, 0);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_white_star_empty);
        bitmapfull = BitmapFactory.decodeResource(getResources(), R.drawable.ic_white_star_full);
        mResultBitmap = Bitmap.createBitmap(mDrawBitmapWidth, mDrawBitmapHeight, Bitmap.Config.ARGB_8888);
        mResultCanvas = new Canvas(mResultBitmap);
    }

    private int getDensity(int value) {
        return (int) (value * density);
    }

    /*
     * Drawing of the view to it's canvas
     *
     * (non-Javadoc)
     * @see android.view.View#onDraw(android.graphics.Canvas)
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mResultCanvas.clipRect(0, 0, mDrawBitmapWidth, mDrawBitmapHeight);
        for (int i = 0; i < 5; i++) {
            mResultCanvas.drawBitmap(bitmap, i * getDensity(60), 0, mDrawingPaint);
        }
        mResultCanvas.clipRect(0, 0, percent * (mDrawBitmapWidth / 100), getDensity(60));
        for (int i = 0; i < 5; i++) {
            mResultCanvas.drawBitmap(bitmapfull, i * getDensity(60), 0, mDrawingPaint);
        }
        //After drawing wheel to result bitmap, draw the result bitmap to the view's canvas.
        //This allows the view to be resized to whatever size the view needs to be.
        canvas.drawBitmap(mResultBitmap, null, mDrawRect, mDrawingPaint);
    }

    /*
     * Determining view size based on constraints from parent views
     *
     * (non-Javadoc)
     * @see android.view.View#onMeasure(int, int)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //Get size requested and size mode
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width, height = 0;

        //Determine Width
        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case MeasureSpec.AT_MOST:
                width = Math.min(mDrawBitmapWidth, widthSize);
                break;
            case MeasureSpec.UNSPECIFIED:
            default:
                width = mDrawBitmapWidth;
                break;
        }

        //Determine Height
        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.AT_MOST:
                height = Math.min(mDrawBitmapHeight, heightSize);
                break;
            case MeasureSpec.UNSPECIFIED:
            default:
                height = mDrawBitmapHeight;
                break;
        }

        setMeasuredDimension(width, height);
    }

    /*
     * Called after onMeasure, returning the actual size of the view before drawing.
     *
     * (non-Javadoc)
     * @see android.view.View#onSizeChanged(int, int, int, int)
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //Change height and width of draw rect to size of view
        //mDrawRect is using to draw the wheel to the view's canvas,
        //setting mDrawRect to the width and height of the view ensures
        //the wheel is drawn correctly to the view
        mDrawRect.set(0, 0, w, h);
    }
}
