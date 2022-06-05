package com.example.myrestaurant.common;

import java.util.Map;

public class RequestToJsonString {

    public static String toJson(Map<String, String> request) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for(String keys : request.keySet()) {
            sb.append("\"").append(keys).append("\"");
            sb.append(":");
            sb.append("\"").append(request.get(keys)).append("\"").append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("}");
        return sb.toString();
    }

}
