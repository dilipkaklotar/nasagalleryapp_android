package com.obvious.nasagallery.data.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.obvious.nasagallery.data.remote.Endpoints;
import com.obvious.nasagallery.data.remote.response.ApiResModel;
import com.obvious.nasagallery.util.MCrypt;
import com.obvious.nasagallery.util.NasaGalleryConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.RequestBody;

public class NasaGalleryRepo extends BaseRepository implements NasaGalleryConstants {


    HashMap<String, String> headers = new HashMap<>();;

    public NasaGalleryRepo(Context context) {
        this.context = context;

        headers.put(KEY_ACCESS_TOKEN, "");
        headers.put(KEY_CONTENT_TYPE, KEY_CONTENT_TYPE_VALUE);
    }

    public MutableLiveData<ApiResModel> signIn(JSONObject jsonObject) {
        return onApiCall(getInterface().signIn(headers, getRequestBodyFromJSON(jsonObject)), new ApiResModel(), Endpoints.SIGN_IN);
    }

    public MutableLiveData<ApiResModel> signUp(JSONObject jsonObject) {
        return onApiCall(getInterface().signUp(headers, getRequestBodyFromJSON(jsonObject)), new ApiResModel(), Endpoints.SIGNUP);
    }

    public RequestBody getRequestBodyFromJSON(JSONObject jsonObject){

        String encodedJSON = null;
        try {
            encodedJSON = new MCrypt().encrypt(jsonObject.toString()).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject jsonParam= new JSONObject();
        try {
            jsonParam.put(KEY_POST_DATA, encodedJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                jsonParam.toString());


        return body;
    }
}
