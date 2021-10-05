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
import com.example.myrestaurant.support.GpsTracker;
import com.example.myrestaurant.support.MyAdapter;
import com.example.myrestaurant.support.MyData;
import com.example.myrestaurant.support.getKakaoAPIData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MyData> myDataset;
    private Button findButton;

    private GpsTracker gpsTracker;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    String address;


    private final int searchNumber = 10;
    String[] placeData = new String[searchNumber];
    String[] linkData = new String[searchNumber];
    BackGroundTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler1);
        findButton = (Button) findViewById(R.id.findButton);



        //gps 기반 주소값 따오는 기능
        gpsTracker = new GpsTracker(MainActivity.this);
        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();
        address = getCurrentAddress(latitude, longitude);
        address = addressChanger(address);

        Toast.makeText(getApplicationContext(), address, Toast.LENGTH_SHORT).show();


        //데이터를 카드 뷰에 넣어서 카드를 만드는 기능
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

        //예시 데이타 추가
        myDataset.add(new MyData("", R.mipmap.spaghetti, "https://place.map.kakao.com/1492599844?service=search_pc"));

        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task = new BackGroundTask();
                task.execute(100);

            }
        });

    }

    public String getCurrentAddress(double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(latitude, longitude, 7);

        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString() + "\n";

    }

    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    //주소 값을 군, 혹은 구 단위까지만 나오도록 변환
    public String addressChanger(String address) {
        String result = "";
        int cursor = 0;
        cursor = address.indexOf("구 ");
        if (cursor == -1) {
            cursor = address.indexOf("군 ");
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
            asyncDialog.setMessage("주변 맛집 찾는 중..");
            asyncDialog.show();
            super.onPreExecute();
        }

        protected Integer doInBackground(Integer... values) {
            try{
                getKakaoAPIData getKakaoAPIData = new getKakaoAPIData(address);
                placeData = getKakaoAPIData.getAPIData();
                for(int i = 0; i<5; i++) {
                    linkData[i] = placeData[i+searchNumber];
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
                    intent.putExtra("URL", linkData[position]);
                    startActivity(intent);
                }
            });

            for(int i = 0; i < 5; i++) {
                myDataset.add(new MyData(placeData[i], R.mipmap.spaghetti, linkData[i]));
            }

        }
    }

}