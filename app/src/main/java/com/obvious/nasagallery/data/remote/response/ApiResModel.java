package com.obvious.nasagallery.data.remote.response;

import com.google.gson.annotations.SerializedName;
import com.obvious.nasagallery.common.viewmodel.BaseViewModel;

public class ApiResModel extends BaseResModel {

    public ApiResModel() {
    }

    @SerializedName("status_code")
    private int status_code;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private String data;

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
