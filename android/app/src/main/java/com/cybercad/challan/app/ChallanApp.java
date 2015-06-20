package com.cybercad.challan.app;

import android.app.Application;

import com.orm.SugarContext;

/**
 * Created by shreyas on 18/6/15.
 */
public class ChallanApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}
