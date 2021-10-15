package com.example.myrestaurant.support;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrestaurant.R;
import com.example.myrestaurant.dto.Restaurant;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder>{
    private List<Restaurant> restaurantArrayList;
    private WebSettings mWebSettings;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public TextView textViewName;
        public TextView textViewCategory;
        public TextView textViewLocation;

        public ViewHolder(final View view) {
            super(view);
            cardView = (CardView)view.findViewById(R.id.card_view_restaurant);
            textViewName = view.findViewById(R.id.card_view_name);
            textViewCategory = view.findViewById(R.id.card_view_category);
            textViewLocation = view.findViewById(R.id.card_view_location);
        }
    }


    //TODO
    //데이터를 바탕으로 카드뷰를 생성할 수 있도록 구현

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


}
