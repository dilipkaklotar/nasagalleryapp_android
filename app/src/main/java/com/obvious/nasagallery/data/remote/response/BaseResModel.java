package com.obvious.nasagallery.data.remote.response;

import org.json.JSONObject;

public class BaseResModel {

    private int resStatusCode;
    private JSONObject errorObject;

    public JSONObject getErrorObject() {
        return errorObject;
    }

    public void setErrorObject(JSONObject error) {
        this.errorObject = error;
    }

    public int getResStatusCode() {
        return resStatusCode;
    }

    public void setResStatusCode(int resStatusCode) {
        this.resStatusCode = resStatusCode;
    }
    public BaseResModel() {
    }
}
