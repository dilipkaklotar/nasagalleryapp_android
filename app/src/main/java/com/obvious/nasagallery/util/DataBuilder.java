package com.obvious.nasagallery.util;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.obvious.nasagallery.data.model.NasaData;

import java.lang.reflect.Type;
import java.util.List;

public class DataBuilder {

    public static  List<NasaData> getNasaDataList (Context mContext){

        List<NasaData> nasaDataList = null;
        String jsonString = Utility.getJsonFromAssets(mContext, Constants.ASSETS_JSON_FILE);

        Gson gson = new Gson();
        Type listUserType = new TypeToken<List<NasaData>>() {
        }.getType();

        nasaDataList = gson.fromJson(jsonString, listUserType);
        return  nasaDataList;
    }
}
