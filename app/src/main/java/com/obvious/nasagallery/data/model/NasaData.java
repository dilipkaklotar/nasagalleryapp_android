package com.obvious.nasagallery.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NasaData implements Serializable {


     @SerializedName("copyright")
     String copyright;

     @SerializedName("date")
     String my_date;

     @SerializedName("explanation")
     String explanation;

     @SerializedName("hdurl")
     String hdurl;

     @SerializedName("media_type")
     String media_type;

     @SerializedName("service_version")
     String service_version;

     @SerializedName("title")
     String title;

     @SerializedName("url")
     String url;

     public String getCopyright() {
          return copyright;
     }

     public void setCopyright(String copyright) {
          this.copyright = copyright;
     }

     public String getMy_date() {
          return my_date;
     }

     public void setMy_date(String my_date) {
          this.my_date = my_date;
     }

     public String getExplanation() {
          return explanation;
     }

     public void setExplanation(String explanation) {
          this.explanation = explanation;
     }

     public String getHdurl() {
          return hdurl;
     }

     public void setHdurl(String hdurl) {
          this.hdurl = hdurl;
     }

     public String getMedia_type() {
          return media_type;
     }

     public void setMedia_type(String media_type) {
          this.media_type = media_type;
     }

     public String getService_version() {
          return service_version;
     }

     public void setService_version(String service_version) {
          this.service_version = service_version;
     }

     public String getTitle() {
          return title;
     }

     public void setTitle(String title) {
          this.title = title;
     }

     public String getUrl() {
          return url;
     }

     public void setUrl(String url) {
          this.url = url;
     }
}
