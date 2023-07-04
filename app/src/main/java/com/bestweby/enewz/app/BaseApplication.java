package com.bestweby.enewz.app;

import androidx.multidex.MultiDexApplication;
import com.bestweby.enewz.listener.NetworkChangeListener;



/**
 * Created by MD Sahidul Islam on 11/7/2016.
 */

public class BaseApplication extends MultiDexApplication {

    public static NetworkChangeListener networkChangeListener;
    private static BaseApplication baseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
    }

    public static synchronized BaseApplication getInstance() {
        return baseApplication;
    }

    public void setNetworkChangedListener(NetworkChangeListener listener) {
        networkChangeListener = listener;
    }
}
