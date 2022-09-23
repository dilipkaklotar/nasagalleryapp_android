package com.obvious.nasagallery;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.obvious.nasagallery.util.ConnectivityReceiver;
import com.obvious.nasagallery.util.PreferenceManager;
import com.obvious.nasagallery.util.SSLCertificateHandler;

public class NasaGalleryApp extends Application {

    final static String CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    IntentFilter intentFilter;
    private ConnectivityReceiver connectivityReceiver;

    private static NasaGalleryApp mInstance;

    private static PreferenceManager preferenceManager;

    public static synchronized NasaGalleryApp getInstance() {
        return mInstance;
    }

    public static PreferenceManager getPreferenceManager() {
        return preferenceManager;
    }

    public static ImageLoader imageLoader;

    public static ImageLoader getImageLoader() {
        return imageLoader;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        SSLCertificateHandler.nuke();

        mInstance = this;

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        preferenceManager = new PreferenceManager(this);

        connectivityReceiver = new ConnectivityReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction(CONNECTIVITY_ACTION);


        imageLoader = ImageLoader.getInstance();

        observeAppLifeCycle();
        initImageLoader(mInstance);
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }


    private void observeAppLifeCycle() {
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new LifecycleObserver() {
            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            public void onAppForeground() {
                registerReceiver(connectivityReceiver, intentFilter);
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            public void onAppBackground() {
                unregisterReceiver(connectivityReceiver);
            }
        });
    }

    public static void initImageLoader(Context context) {

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        //if (URL.getENVIRONMENT().equalsIgnoreCase(Constants.KEY_DEBUG))
        // config.writeDebugLogs(); // Remove for release app
        ImageLoader.getInstance().init(config.build());
    }
}
