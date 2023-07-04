package com.bestweby.enewz.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;

import com.bestweby.enewz.R;
import com.bestweby.enewz.app.BaseActivity;
import com.bestweby.enewz.cache.constant.AppConstants;
import com.bestweby.enewz.databinding.ActivityWebContentLayoutBinding;
import com.bestweby.enewz.helper.AppHelper;

/**
 * Created by Sahidul Islam on 06-Jul-19.
 */
public class WebContentActivity extends BaseActivity {

    private ActivityWebContentLayoutBinding binding;

    private String pageTitle, contentURL;
    private boolean isfromNotification;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVars();
        initView();
        initFunctionality();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (isfromNotification)
                    startActivity(new Intent(WebContentActivity.this, NewsTemplateActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (isfromNotification)
            startActivity(new Intent(WebContentActivity.this, NewsTemplateActivity.class));
        finish();
    }

    private void initVars() {
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            if (bundle.containsKey(AppConstants.BUNDLE_PAGE_TITLE))
                pageTitle = bundle.getString(AppConstants.BUNDLE_PAGE_TITLE);

            if (bundle.containsKey(AppConstants.BUNDLE_WEB_URL))
                contentURL = bundle.getString(AppConstants.BUNDLE_WEB_URL);

            if (bundle.containsKey(AppConstants.BUNDLE_FROM_NOTIFICATION)) {
                isfromNotification = bundle.getBoolean(AppConstants.BUNDLE_FROM_NOTIFICATION, false);
            }
        }
    }

    private void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_content_layout);

        setToolbar(binding.primaryToolbar.toolbar, binding.primaryToolbar.toolbarTitle, pageTitle);
    }

    private void initFunctionality() {
        progressDialog.show();
        WebSettings webSetting = binding.contentWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);

        binding.contentWebView.setWebViewClient(new WebViewClient() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request) {
                webView.loadUrl(AppHelper.getValidUrl(request.getUrl().toString()));
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressDialog.dismiss();
                binding.contentWebView.setVisibility(View.VISIBLE);
            }

        });

        // load web view
        binding.contentWebView.loadUrl(AppHelper.getValidUrl(contentURL));
    }
}
