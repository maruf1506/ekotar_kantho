package com.bestweby.enewz.activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.bestweby.enewz.R;
import com.bestweby.enewz.app.BaseActivity;
import com.bestweby.enewz.databinding.ActivitySplashLayoutBinding;
import com.bestweby.enewz.helper.AppHelper;


/**
 * Created by Md Sahidul Islam on 18-Jan-19.
 */

public class SplashActivity extends BaseActivity {

    private ActivitySplashLayoutBinding binding;

    private static final long Splash_Time = 2500;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initFunctionality();

        deepLinkHandler();
    }

    private void initView() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_layout);
    }

    private void initFunctionality() {
        if (AppHelper.isNetworkAvailable(context)) {
            binding.getRoot().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, NewsTemplateActivity.class));
                    finish();
                }
            }, Splash_Time);

        }
        AppHelper.noInternetWarning(context, binding.getRoot());
    }

    private void deepLinkHandler() {
        Intent intent = getIntent();
        Uri data = intent.getData();

        if (data != null && data.isHierarchical()) {
            Log.e("deepData: ", data.toString());
        }
    }
}
