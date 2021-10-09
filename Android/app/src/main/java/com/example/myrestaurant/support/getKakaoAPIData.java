package com.example.myrestaurant.support;

import android.util.Log;

import com.example.myrestaurant.dto.Restaurant;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class getKakaoAPIData {

    private String location;
    private final String APIKEY =  "0badefdcbbc22dd6ec5c768ea1442ec5";
    private final int display = 10;
    private static String[] dataString;
    private static List<Restaurant> restaurantList;

    public getKakaoAPIData(String location) {
        this.location = location;
        dataString = new String[display*2];
        restaurantList = new ArrayList<>();
    }

    public List<Restaurant> getAPIData() {

        try {
            location = URLEncoder.encode(location+" 맛집","UTF-8");
            String apiURL = "https://dapi.kakao.com/v2/local/search/keyword.json?query="+location+"&page=1&size="+display;
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            String auth = "KakaoAK " + APIKEY;
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Requested-With", "curl");
            con.setRequestProperty("Authorization", auth);

            int responseCode = con.getResponseCode();
            BufferedReader br;

            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "UTF-8"));
            }
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }

            br.close();
            con.disconnect();

            String data = sb.toString();
            String[] array;
            array = data.split("\"");

            System.out.println("array : " +array.toString());

            String[] title = new String[display];
            String[] link = new String[display];
            String[] address = new String[display];
            String[] category = new String[display];

            int k = 0;

            for (int i = 0; i < array.length; i++) {
                if (array[i].equals("address_name")) {
                    address[k] = array[i + 2];
                }
                if (array[i].equals("category_name")) {
                    category[k] = array[i + 2];
                }
                if (array[i].equals("place_name")){
                    title[k] = array[i + 2];
                }
                if (array[i].equals("place_url")) {
                    link[k] = array[i + 2];
                    Restaurant restaurant = new Restaurant();
                    restaurant.setCategory(category[k]);
                    restaurant.setLocation(address[k]);
                    restaurant.setLink(link[k]);
                    restaurant.setName(title[k]);
                    restaurantList.add(restaurant);
                    k++;
                }
            }

        }catch (Exception e) {
            System.out.println(e);
        }

        return restaurantList;
    }
}
