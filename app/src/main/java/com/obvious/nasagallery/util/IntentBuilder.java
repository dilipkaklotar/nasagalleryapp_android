package com.obvious.nasagallery.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class IntentBuilder {

    Intent intent;

    public IntentBuilder(Context context, Class<?> classActivity) {
        this.intent = new Intent(context, classActivity);
    }

    public void withExtra(String key, String val) {
        intent.putExtra(key, val);
    }

    public void withBundle(Bundle bundle) {
        intent.putExtras(bundle);
    }

    public Intent getIntent() {
        return intent;
    }


}
