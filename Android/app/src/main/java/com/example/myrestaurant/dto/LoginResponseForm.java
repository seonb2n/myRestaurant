package com.example.myrestaurant.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class LoginResponseForm {

    @SerializedName("result")
    private String result;

    @SerializedName("message")
    private String responseMessage;

    @SerializedName("data")
    private Data data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
