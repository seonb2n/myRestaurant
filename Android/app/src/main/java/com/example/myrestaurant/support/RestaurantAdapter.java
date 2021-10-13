package com.example.myrestaurant.support;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrestaurant.R;
import com.example.myrestaurant.dto.Restaurant;

import java.util.ArrayList;

public class RestaurantAdapter {
    private ArrayList<Restaurant> restaurantArrayList;
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
}
