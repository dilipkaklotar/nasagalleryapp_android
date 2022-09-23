package com.obvious.nasagallery.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.obvious.nasagallery.R;
import com.obvious.nasagallery.ui.MainActivity;
import com.obvious.nasagallery.util.IntentBuilder;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        // Launch MainActivity after 2000 milliseconds delay
        new Handler().postDelayed(() -> {

            // Intent Builder is common class for intent
            startActivity(new IntentBuilder(SplashActivity.this, MainActivity.class)
                    .getIntent());
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();

        }, 2000);
    }
}