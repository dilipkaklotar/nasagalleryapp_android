package com.obvious.nasagallery.data.remote;

import com.obvious.nasagallery.data.remote.response.ApiResModel;
import com.obvious.nasagallery.data.remote.response.Endpoints;

import java.util.HashMap;

import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface NetworkService {

    @POST(Endpoints.SIGN_IN)
    Single<Response<ApiResModel>> signIn(@HeaderMap HashMap<String, String> headers, @Body RequestBody params);

    @POST(Endpoints.SIGNUP)
    Single<Response<ApiResModel>> signUp(@HeaderMap HashMap<String, String> headers, @Body RequestBody params);



}
