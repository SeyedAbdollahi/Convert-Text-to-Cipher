package com.language.ra.myapplication;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * Created by Reza Abdollahi on 25/03/2018.
 */

public class setfont extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Vazir-Light-FD.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

}
