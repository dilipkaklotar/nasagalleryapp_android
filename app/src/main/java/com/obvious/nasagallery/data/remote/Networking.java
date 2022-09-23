package com.obvious.nasagallery.data.remote;


import android.content.Context;

import androidx.annotation.NonNull;

import com.obvious.nasagallery.utils.Urls;
import com.obvious.nasagallery.util.Constants;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Networking {

    private Context mContext;

    public Networking(Context context) {
        this.mContext = context;
    }

    private Retrofit.Builder getRetrofitBuilder() {
        return new Retrofit.Builder()
                .baseUrl(Urls.getBaseUrl())
                .client(getHttpClient())
                .addConverterFactory(GsonConverterFactory.create());
    }

    public Retrofit getClientWithRxJavaFactory() {
        return getRetrofitBuilder().addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
    }

    final TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[]{};
                }
            }
    };

    private OkHttpClient getHttpClient() {

        OkHttpClient.Builder httpClient = null;
        try {

            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            ConnectionSpec spec = new
                    ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .tlsVersions(TlsVersion.TLS_1_2) //TLSv1.2
                    .cipherSuites(
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                    .build();

            httpClient = new OkHttpClient.Builder();
            httpClient.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);

            httpClient.protocols(Arrays.asList(Protocol.HTTP_2, Protocol.HTTP_1_1));
            httpClient.connectTimeout(0, TimeUnit.SECONDS)
                    .writeTimeout(0, TimeUnit.SECONDS)
                    .readTimeout(0, TimeUnit.SECONDS);
            if (Urls.getENVIRONMENT().equalsIgnoreCase(Constants.KEY_DEBUG)) {
                httpClient.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
            }

            httpClient.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return Urls.Hostname.equalsIgnoreCase(hostname);
                }
            });

            httpClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {

                    Request request = chain.request();
                    Response response = chain.proceed(request);
                    response.code();//status code
                    return response;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpClient.build();
    }

    public class UserAgentInterceptor implements Interceptor {
        private final String mUserAgent;

        public UserAgentInterceptor(String userAgent) {
            mUserAgent = userAgent;
        }

        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request request = chain.request()
                    .newBuilder()
                    .header("User-Agent", mUserAgent)
                    .build();
            return chain.proceed(request);
        }
    }

    public static SSLContext trustEveryone() {

        SSLContext context = null;
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            context = SSLContext.getInstance("TLSv1.2"); //TLSv1.2
            context.init(null, new X509TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
        } catch (Exception e) { // should never happen
            e.printStackTrace();
        }


        return context;
    }
}
