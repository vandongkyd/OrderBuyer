package com.fernandocejas.android10.order.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fernandocejas.android10.R;
import com.fernandocejas.android10.order.presentation.model.EventModel;

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

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    public interface OnItemClickListener {
        void onItemClicked(EventModel eventModel);
    }

    private List<EventModel> eventsCollection;
    private final LayoutInflater layoutInflater;

    private final Context context;

    private EventAdapter.OnItemClickListener onItemClickListener;

    @Inject
    EventAdapter(Context context) {
        this.context = context;
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.eventsCollection = Collections.emptyList();
    }

    @Override
    public int getItemCount() {
        return (this.eventsCollection != null) ? this.eventsCollection.size() : 0;
    }

    @Override
    public EventAdapter.EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = this.layoutInflater.inflate(R.layout.row_event, parent, false);
        return new EventAdapter.EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventAdapter.EventViewHolder holder, final int position) {
        final EventModel eventModel = this.eventsCollection.get(position);
        Glide.with(context)
                .load(eventModel.getImage())
                .into(holder.imv_image);
        holder.itemView.setOnClickListener(v -> {
            if (EventAdapter.this.onItemClickListener != null) {
                EventAdapter.this.onItemClickListener.onItemClicked(eventModel);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setEventsCollection(Collection<EventModel> eventsCollection) {
        this.validateCollection(eventsCollection);
        this.eventsCollection = (List<EventModel>) eventsCollection;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(EventAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateCollection(Collection<EventModel> eventsCollection) {
        if (eventsCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.imv_image)
        ImageView imv_image;

        EventViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
