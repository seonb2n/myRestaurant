package com.example.myrestaurant.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrestaurant.R;
import com.example.myrestaurant.dto.Restaurant;
import com.example.myrestaurant.support.GpsTracker;
import com.example.myrestaurant.support.MyAdapter;
import com.example.myrestaurant.support.MyData;
import com.example.myrestaurant.support.getKakaoAPIData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.http.Tag;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MyData> myDataset;
    private Button findButton;
    private Button restaurantButton;

    private GpsTracker gpsTracker;

    String address;


    private final int searchNumber = 10;
    List<Restaurant> restaurantList = new ArrayList<>();
    String[] linkData = new String[searchNumber];
    BackGroundTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_main);
        mRecyclerView = findViewById(R.id.recycler1);
        findButton = findViewById(R.id.findButton);
        restaurantButton = findViewById(R.id.restaurantButton);

        gpsTracker = new GpsTracker(MainActivity.this);
        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();
        address = getCurrentAddress(latitude, longitude);
        address = addressChanger(address);

        Toast.makeText(getApplicationContext(), address, Toast.LENGTH_SHORT).show();

        //???????????? ?????? ?????? ????????? ????????? ????????? ??????
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        myDataset = new ArrayList<>();
        final MyAdapter mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(getApplicationContext(), "Click the Button!", Toast.LENGTH_SHORT).show();
            }
        });

        //?????? ????????? ??????
        myDataset.add(new MyData(null, R.mipmap.spaghetti, "https://place.map.kakao.com/1492599844?service=search_pc"));

        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task = new BackGroundTask();
                task.execute(100);

            }
        });

        restaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyRestaurantActivity.class);
                startActivity(intent);
            }
        });
    }

    public String getCurrentAddress(double latitude, double longitude) {

        //????????????... GPS??? ????????? ??????
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(latitude, longitude, 7);

        } catch (IOException ioException) {
            //???????????? ??????
            Toast.makeText(this, "???????????? ????????? ????????????", Toast.LENGTH_LONG).show();
            return "???????????? ????????? ????????????";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "????????? GPS ??????", Toast.LENGTH_LONG).show();
            return "????????? GPS ??????";

        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "?????? ?????????", Toast.LENGTH_LONG).show();
            return "?????? ?????????";

        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString() + "\n";

    }

    public String addressChanger(String address) {
        String result = "";
        int cursor = 0;
        cursor = address.indexOf("??? ");
        if (cursor == -1) {
            cursor = address.indexOf("??? ");
        }

        cursor += 2;

        result = address.substring(cursor, cursor + 3);

        return result;
    }

    class BackGroundTask extends AsyncTask<Integer, Integer, Integer> {

        ProgressDialog asyncDialog;

        @Override
        protected void onPreExecute() {
            asyncDialog = new ProgressDialog(MainActivity.this);
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("?????? ?????? ?????? ???..");
            asyncDialog.show();
            super.onPreExecute();
        }

        protected Integer doInBackground(Integer... values) {
            try {
                getKakaoAPIData getKakaoAPIData = new getKakaoAPIData(address);
                restaurantList = getKakaoAPIData.getAPIData();
                for (int i = 0; i < 5; i++) {
                    linkData[i] = restaurantList.get(i).getLink();
                    String category = restaurantList.get(i).getCategory().replaceAll("[u]003e", "");
                    restaurantList.get(i).setCategory(category);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            return 1;
        }

        protected void onPostExecute(Integer result) {
            asyncDialog.dismiss();
            super.onPostExecute(result);

            mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            mRecyclerView.setLayoutManager(mLayoutManager);
            myDataset = new ArrayList<>();
            final MyAdapter mAdapter = new MyAdapter(myDataset);
            mRecyclerView.setAdapter(mAdapter);

            mAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    Intent intent = new Intent(getApplicationContext(), WebActivity.class);
                    intent.putExtra("Restaurant", restaurantList.get(position));
                    startActivity(intent);
                }
            });

            for (int i = 0; i < 5; i++) {
                myDataset.add(new MyData(restaurantList.get(i), R.mipmap.spaghetti, linkData[i]));
            }

        }

    }
}