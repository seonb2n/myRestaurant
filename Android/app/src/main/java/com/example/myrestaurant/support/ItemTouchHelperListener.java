package com.example.myrestaurant.support;

import androidx.recyclerview.widget.RecyclerView;

public interface ItemTouchHelperListener  {
    boolean onItemMove(int from_position, int to_position);
    void onItemSwipe(int position);
    void onLeftClick(int position, RecyclerView.ViewHolder viewHolder);
    void onRightClick(int position, RecyclerView.ViewHolder viewHolder);
}
