package com.github.guwenk.todoproject.app;

import android.app.Application;

import com.github.guwenk.todoproject.R;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/OpenSans-Light.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
    }

}
