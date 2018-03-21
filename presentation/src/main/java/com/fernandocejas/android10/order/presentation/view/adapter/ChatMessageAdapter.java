package com.fernandocejas.android10.order.presentation.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.presentation.model.MessageModel;
import com.fernandocejas.android10.order.presentation.utils.Constants;
import com.fernandocejas.android10.order.presentation.view.ChatMessageAdapterView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 *
 */

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.MessageViewHolder> implements ChatMessageAdapterView {

    public static final int ITEM_TYPE_YOU = 101;
    public static final int ITEM_TYPE_ME = 102;

    public interface OnItemClickListener {
        void onItemClicked(MessageModel messageModel);
    }

    private List<MessageModel> messageModels;
    private LayoutInflater layoutInflater;

    private final Context context;

    private ChatMessageAdapter.OnItemClickListener onItemClickListener;

    private String avatarProvider;

    @Inject
    ChatMessageAdapter(Context context) {
        this.context = context;
        this.messageModels = Collections.emptyList();
    }

    @Override
    public int getItemCount() {
        return (this.messageModels != null) ? this.messageModels.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        final MessageModel messageModel = this.messageModels.get(position);
        if ((messageModel.getSender() + "").equals(Constants.USER_ID)) {
            return ITEM_TYPE_ME;
        }
        return ITEM_TYPE_YOU;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (this.layoutInflater == null) {
            this.layoutInflater =
                    (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (viewType == ITEM_TYPE_YOU) {
            final View view = this.layoutInflater.inflate(R.layout.row_chat_message_you, parent, false);
            return new ChatMessageAdapter.MessageViewHolder_You(view);
        } else {
            final View view = this.layoutInflater.inflate(R.layout.row_chat_message_me, parent, false);
            return new ChatMessageAdapter.MessageViewHolder_Me(view);
        }
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, final int position) {
        final MessageModel messageModel = this.messageModels.get(position);
        //show message
        showMessageInView(holder, messageModel.getMessage());
        //show time
        showTimeInView(holder, messageModel.getDatetime());
        //action events
        holder.itemView.setOnClickListener(v -> {
            if (ChatMessageAdapter.this.onItemClickListener != null) {
                ChatMessageAdapter.this.onItemClickListener.onItemClicked(messageModel);
            }
        });
        if (holder instanceof MessageViewHolder_You) {
            //show avatar
            showAvatarInView(holder, avatarProvider);
            //change text color
            holder.tv_message.setTextColor(Color.parseColor("#0B0B0B"));
            holder.tv_time_text.setTextColor(Color.parseColor("#7F7F7F"));
            //change background color
            this.changeBackgroundColor(holder.lyt_text, "#EEEEEE");

        } else {
            //show avatar
            showAvatarInView(holder, Constants.USER_AVATAR);
            //change text color
            holder.tv_message.setTextColor(Color.parseColor("#FFFFFF"));
            holder.tv_time_text.setTextColor(Color.parseColor("#FFFFFF"));
            //change background color
            this.changeBackgroundColor(holder.lyt_text, "#46A8C1");
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setMessageModels(Collection<MessageModel> messageModels) {
        this.validateCollection(messageModels);
        this.messageModels = (List<MessageModel>) messageModels;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(ChatMessageAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setAvatarProvider(String avatarProvider) {
        this.avatarProvider = avatarProvider;
    }

    @Override
    public void showAvatarInView(MessageViewHolder viewHolder, String url) {
        loadImageFromUrl(context, viewHolder.imv_avatar, url, true, true);
    }

    @Override
    public void showMessageInView(MessageViewHolder viewHolder, String message) {
        if (message.contains("http://") || message.contains("https://")) {
            viewHolder.lyt_media.setVisibility(View.VISIBLE);
            viewHolder.lyt_text.setVisibility(View.GONE);
            this.loadImageFromUrl(context, viewHolder.imv_message, message, false, false);
        } else {
            viewHolder.lyt_media.setVisibility(View.GONE);
            viewHolder.lyt_text.setVisibility(View.VISIBLE);
            viewHolder.tv_message.setText(message);
        }
    }

    @Override
    public void showTimeInView(MessageViewHolder viewHolder, String time) {
        viewHolder.tv_time_text.setText(time);
        viewHolder.tv_time.setText(time);
    }

    private void validateCollection(Collection<MessageModel> eventsCollection) {
        if (eventsCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    private void changeBackgroundColor(LinearLayout lyt_main_message, String color) {
        LayerDrawable layerDrawable = (LayerDrawable) ContextCompat.getDrawable(context, R.drawable.chat_message_background);
        GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable
                .findDrawableByLayerId(R.id.solidDrawable);
        gradientDrawable.setColor(Color.parseColor(color)); // change color
        lyt_main_message.setBackground(layerDrawable);
    }

    private void loadImageFromUrl(Context context, ImageView view, String url, boolean isCircle, boolean hasDefault) {
        if (url == null || url.isEmpty()) {
            if (hasDefault) {
                //show default avatar if we don't have url to show
                Glide.with(context)
                        .load(R.drawable.default_avatar)
                        .asBitmap()
                        .into(new BitmapImageViewTarget(view) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                view.setImageDrawable(circularBitmapDrawable);
                            }
                        });
            }
            return;
        }
        if (isCircle) {
            Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .into(new BitmapImageViewTarget(view) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            view.setImageDrawable(circularBitmapDrawable);
                        }
                    });
        } else {
            Glide.with(context)
                    .load(url)
                    .into(view);
        }
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.lyt_main_message)
        FrameLayout lyt_main_message;
        @Bind(R.id.lyt_text)
        LinearLayout lyt_text;
        @Bind(R.id.lyt_media)
        LinearLayout lyt_media;
        @Bind(R.id.imv_avatar)
        ImageView imv_avatar;
        @Bind(R.id.tv_message)
        TextView tv_message;
        @Bind(R.id.imv_message)
        ImageView imv_message;
        @Bind(R.id.tv_time_text)
        TextView tv_time_text;
        @Bind(R.id.tv_time)
        TextView tv_time;

        MessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class MessageViewHolder_Me extends MessageViewHolder {

        MessageViewHolder_Me(View itemView) {
            super(itemView);
        }
    }

    public static class MessageViewHolder_You extends MessageViewHolder {

        MessageViewHolder_You(View itemView) {
            super(itemView);
        }
    }

}
