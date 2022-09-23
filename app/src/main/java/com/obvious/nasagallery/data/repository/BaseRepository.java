package com.obvious.nasagallery.data.repository;


import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;


import com.obvious.nasagallery.data.remote.NetworkService;
import com.obvious.nasagallery.data.remote.Networking;
import com.obvious.nasagallery.data.remote.response.BaseResModel;
import com.obvious.nasagallery.util.GSONBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class BaseRepository {

    protected static String TAG = BaseRepository.class.getName();
    protected Context context;

    protected NetworkService getInterface() {
        return new Networking(context).getClientWithRxJavaFactory().create(NetworkService.class);
    }

    protected <T> MutableLiveData<T> onApiCall(Single<Response<T>> ai, T obj, String apiName) {
        CompositeDisposable disposable = new CompositeDisposable();
        final MutableLiveData<T> mutableLiveData = new MutableLiveData<>();
        try {
            disposable.add(ai
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError(Throwable::printStackTrace)
                    .onErrorReturn(new Function<Throwable, Response<T>>() {
                        @Override
                        public Response<T> apply(Throwable throwable)  {

                            throwable.printStackTrace();
                            return null;
                        }
                    })
                    .subscribeWith(new DisposableSingleObserver<Response<T>>() {
                        @Override
                        public void onSuccess(Response<T> t) {
                            disposable.dispose();
                            if (t.isSuccessful()) {
                                GSONBuilder.getString(t.body());
                                try {
                                    if (t.body() != null) {
                                        T a = t.body();
                                        ((BaseResModel) a).setResStatusCode(t.code());
                                        mutableLiveData.postValue(a);
                                    } else {

                                        ((BaseResModel) obj).setResStatusCode(t.code());
                                        mutableLiveData.postValue(obj);
                                    }
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                            } else {

                                ((BaseResModel) obj).setResStatusCode(t.code());
                                JSONObject jsonObject = null;
                                if (t.errorBody() != null) {
                                    try {
                                        jsonObject = new JSONObject(t.errorBody().string());
                                    } catch (JSONException e) {
                                        jsonObject = new JSONObject();
                                        e.printStackTrace();
                                    } catch (Exception e) {
                                        jsonObject = new JSONObject();
                                        e.printStackTrace();
                                    }
                                }

                                ((BaseResModel) obj).setErrorObject(jsonObject);



                                mutableLiveData.postValue(obj);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, apiName + " Failure " + e.getMessage());
                            e.printStackTrace();
                            disposable.dispose();
                            mutableLiveData.setValue(null);
                        }

                    }));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mutableLiveData;
    }
}
