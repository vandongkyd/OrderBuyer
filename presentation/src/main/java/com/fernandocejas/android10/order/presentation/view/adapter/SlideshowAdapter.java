package com.fernandocejas.android10.order.presentation.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.presentation.model.ImageModel;
import com.fernandocejas.android10.order.presentation.view.SlideshowAdapterView;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by Administrator on 3/30/2017.
 */

public class SlideshowAdapter extends PagerAdapter implements SlideshowAdapterView {

    private Context context;

    private LayoutInflater inflater;

    private ArrayList<ImageModel> mList;

    @Inject
    public SlideshowAdapter(Context context) {
        this.context = context;
        mList = new ArrayList<>();
    }

    public void replaceWith(Collection<ImageModel> newValues) {
        mList.clear();
        if (newValues != null) {
            mList.addAll(newValues);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((FrameLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row_slideshow, container,
                false);
        view.setTag("pv_" + position);
        container.addView(view);
        ViewHolder holder = new ViewHolder(view);
        ImageModel imageModel = mList.get(position);
        showImageInView(holder, imageModel.getImage());
        return view;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }

    @Override
    public void showImageInView(SlideshowAdapter.ViewHolder viewHolder, String url) {
        viewHolder.progress_loading.setVisibility(View.VISIBLE);
        Glide.with(context)
                .load(url)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        viewHolder.progress_loading.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(viewHolder.imv_photo)
        ;
    }

    public class ViewHolder {
        public final ImageView imv_photo;
        public final ProgressBar progress_loading;

        public ViewHolder(View view) {
            imv_photo = (ImageView) view.findViewById(R.id.imv_photo);
            progress_loading = (ProgressBar) view.findViewById(R.id.progress_loading);
            progress_loading.setVisibility(View.VISIBLE);
        }
    }

}
