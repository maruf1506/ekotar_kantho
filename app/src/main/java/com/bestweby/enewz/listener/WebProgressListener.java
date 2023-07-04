package com.bestweby.enewz.listener;

/**
 * Created by Sahidul Islam on 25-Oct-19.
 */
public interface WebProgressListener {
    void onStart();

    void onProgress(int progress);

    void onFinished();

    void onNetworkError();
}
