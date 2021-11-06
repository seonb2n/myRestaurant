package com.example.myrestaurant.support;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrestaurant.R;
import com.example.myrestaurant.dto.Restaurant;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> implements ItemTouchHelperListener{
    private List<Restaurant> restaurantArrayList;
    private WebSettings mWebSettings;
    private RestaurantAdapter.OnItemClickListener mListener;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public TextView textViewName;
        public TextView textViewCategory;
        public TextView textViewLocation;
        public Button cardViewButton;

        public ViewHolder(final View view) {
            super(view);
            cardView = (CardView)view.findViewById(R.id.card_view_restaurant);
            textViewName = view.findViewById(R.id.card_view_name);
            textViewCategory = view.findViewById(R.id.card_view_category);
            textViewLocation = view.findViewById(R.id.card_view_location);
            cardViewButton = (Button) view.findViewById(R.id.restaurantViewButton);

            cardViewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAbsoluteAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener != null) {
                            mListener.onItemClick(v, pos);
                        }
                    }
                }
            });

        }
    }

    public RestaurantAdapter(List<Restaurant> myRestaurants) {
        restaurantArrayList = myRestaurants;
    }

    @Override
    public RestaurantAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_restaurant, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewName.setText(restaurantArrayList.get(position).getName());
        holder.textViewCategory.setText(restaurantArrayList.get(position).getCategory());
        holder.textViewLocation.setText(restaurantArrayList.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return restaurantArrayList.size();
    }

    //클릭 이벤트 구현
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(RestaurantAdapter.OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public boolean onItemMove(int from_position, int to_position) {
        Restaurant restaurant = restaurantArrayList.get(from_position);
        restaurantArrayList.remove(from_position);
        restaurantArrayList.add(to_position, restaurant);
        notifyItemMoved(from_position, to_position);
        return true;
    }

    @Override
    public void onItemSwipe(int position) {
        restaurantArrayList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onLeftClick(int position, RecyclerView.ViewHolder viewHolder) {

    }

    @Override
    public void onRightClick(int position, RecyclerView.ViewHolder viewHolder) {
        restaurantArrayList.remove(position);
        notifyItemRemoved(position);
    }

}
