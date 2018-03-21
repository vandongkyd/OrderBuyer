
package com.fernandocejas.android10.order.presentation.view.adapter;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.loopeer.itemtouchhelperextension.ItemTouchHelperExtension;

public class ItemTouchHelperCallback extends ItemTouchHelperExtension.Callback {

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof ProductAdapter.ItemNoSwipeViewHolder) {
            return 0;
        }
        if (viewHolder instanceof PaymentAdapter.ItemNoSwipeViewHolder) {
            return 0;
        }
        return makeMovementFlags(ItemTouchHelper.UP|ItemTouchHelper.DOWN, ItemTouchHelper.START);

    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        /*if (recyclerView.getAdapter() instanceof ProductAdapter) {
            ProductAdapter adapter = (ProductAdapter) recyclerView.getAdapter();
            adapter.move(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        } else if (recyclerView.getAdapter() instanceof PaymentAdapter) {
            PaymentAdapter adapter = (PaymentAdapter) recyclerView.getAdapter();
            adapter.move(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }*/
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (dY != 0 && dX == 0)
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        if (viewHolder instanceof ProductAdapter.ItemBaseViewHolder) {
            ProductAdapter.ItemBaseViewHolder holder = (ProductAdapter.ItemBaseViewHolder) viewHolder;
            if (viewHolder instanceof ProductAdapter.ItemSwipeWithActionWidthNoSpringViewHolder) {
                if (dX < -holder.view_list_repo_action_container.getWidth()) {
                    dX = -holder.view_list_repo_action_container.getWidth();
                }
                holder.view_list_main_content.setTranslationX(dX);
                return;
            }
            holder.view_list_main_content.setTranslationX(dX);
        } else if (viewHolder instanceof PaymentAdapter.ItemBaseViewHolder) {
            PaymentAdapter.ItemBaseViewHolder holder = (PaymentAdapter.ItemBaseViewHolder) viewHolder;
            if (viewHolder instanceof PaymentAdapter.ItemSwipeWithActionWidthNoSpringViewHolder) {
                if (dX < -holder.view_list_repo_action_container.getWidth()) {
                    dX = -holder.view_list_repo_action_container.getWidth();
                }
                holder.view_list_main_content.setTranslationX(dX);
                return;
            }
            holder.view_list_main_content.setTranslationX(dX);
        }
    }
}
