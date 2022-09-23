package com.obvious.nasagallery.utils;

import com.obvious.nasagallery.util.Constants;

public class Urls {

    // Release

    public static String Hostname = "";

    public static String BASE_URL = "";

    public static String COMMON_URL = "";

    public static String DOMAIN_NAME = "";

    public static String VERSION = "";

    public static String getBaseUrl() {
        return  BASE_URL + VERSION;
    }

    public static String getCommonUrl(){
        return COMMON_URL;
    }

    public static String getDomainName(){
        return DOMAIN_NAME;
    }

    public static String ENVIRONMENT = Constants.KEY_RELEASE;

    public static String getENVIRONMENT() {return ENVIRONMENT;}
}
