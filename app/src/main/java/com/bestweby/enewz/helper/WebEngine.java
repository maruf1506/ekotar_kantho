package com.bestweby.enewz.helper;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.webkit.DownloadListener;
import android.webkit.MimeTypeMap;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bestweby.enewz.R;
import com.bestweby.enewz.cache.constant.AppConstants;
import com.bestweby.enewz.cache.preference.AppPreference;
import com.bestweby.enewz.listener.WebProgressListener;
import com.bestweby.enewz.receiver.NetworkChangeReceiver;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Sahidul Islam on 25-Oct-19.
 */

public class WebEngine {

    private Activity mActivity;
    private Context mContext;

    private WebView mWebView;
    private Fragment mFragment;

    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mFilePathCallback;

    private String downloadUrl;
    private WebProgressListener mWebListener;


    public WebEngine(Activity mActivity, WebView webView) {
        this.mActivity = mActivity;
        this.mContext = mActivity.getApplicationContext();
        this.mWebView = webView;
    }

    public WebEngine(Activity mActivity, WebView webView, Fragment fragment) {
        this.mActivity = mActivity;
        this.mContext = mActivity.getApplicationContext();
        this.mWebView = webView;
        this.mFragment = fragment;
    }

    public void initView() {
        // Get the web view settings instance
        WebSettings settings = mWebView.getSettings();

        // Enable java script in web view
        settings.setJavaScriptEnabled(true);

        // Enable and setup web view cache
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        // Enable disable images in web view
        // settings.blockNetworkImage = false
        // Whether the WebView should load image resources
        // settings.loadsImagesAutomatically = true


        // More web view settings
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            settings.setSafeBrowsingEnabled(true);  // api 26
        }
        //settings.pluginState = WebSettings.PluginState.ON
        settings.setLoadWithOverviewMode(true);
        //settings.setUseWideViewPort(true);
        // settings.javaScriptCanOpenWindowsAutomatically = true
        // settings.mediaPlaybackRequiresUserGesture = false

        // More optional settings, you can enable it by yourself
        settings.setDomStorageEnabled(true);
        // settings.setSupportMultipleWindows(true)
        settings.setAllowContentAccess(true);
        //settings.setGeolocationEnabled(true);
        settings.setAllowUniversalAccessFromFileURLs(true);

        settings.setAllowFileAccess(true);

        // WebView settings
        mWebView.setFitsSystemWindows(true);

/*        boolean zoomControll = AppPreference.getInstance(mContext).isZoomEnabled();
        settings.setSupportZoom(zoomControll);
        settings.setBuiltInZoomControls(zoomControll);
        settings.setDisplayZoomControls(zoomControll);*/

/*        boolean cookieControll = AppPreference.getInstance(mContext).isCookieEnabled();
        CookieManager.getInstance().setAcceptCookie(cookieControll);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, cookieControll);
        }*/

        String textSize = AppPreference.getInstance(mContext).getTextSize();
        if (textSize.equals(mContext.getResources().getString(R.string.small_text))) {
            settings.setTextZoom(80);
        } else if (textSize.equals(mContext.getResources().getString(R.string.default_text))) {
            settings.setTextZoom(100);
        } else if (textSize.equals(mContext.getResources().getString(R.string.large_text))) {
            settings.setTextZoom(120);
        }
    }

    public void initListener(WebProgressListener webProgressListener) {
        mWebListener = webProgressListener;

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mWebListener.onProgress(newProgress);
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                //return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);

                if (filePathCallback != null) {
                    mFilePathCallback.onReceiveValue(null);
                }

                mFilePathCallback = filePathCallback;
                initFileChooser();

                return true;
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //return super.shouldOverrideUrlLoading(view, request);

                Log.e("request: ", request.getUrl().toString());

                ArrayList<String> fileExtensions = new ArrayList<>(Arrays.asList(mActivity.getResources().getStringArray(R.array.web_extensions)));
                String extention = MimeTypeMap.getFileExtensionFromUrl(request.getUrl().toString());

                if (fileExtensions.contains(extention)) {
                    downloadUrl = request.getUrl().toString();
                    downloadFile();
                } else {
                    loadWebPage(request.getUrl().toString());
                }

                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mWebListener.onStart();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mWebListener.onFinished();
            }
        });

        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                downloadUrl = url;
                downloadFile();
            }
        });
    }

    public void loadWebPage(String siteUrl) {
        if (NetworkChangeReceiver.isNetworkConnected()) {
            try {
                ArrayList<String> webProtocols = new ArrayList<>(Arrays.asList(mActivity.getResources().getStringArray(R.array.web_protocol)));
              //  ArrayList<String> webExtensions = new ArrayList<>(Arrays.asList(mActivity.getResources().getStringArray(R.array.web_extensions)));

                URI webUri = new URI(siteUrl);
                String extention = MimeTypeMap.getFileExtensionFromUrl(siteUrl);

                if (webProtocols.contains(webUri.getScheme())) {
                    openNativeApp(siteUrl);
                } else if (siteUrl.contains("?target=blank")) {
                    openNativeApp(siteUrl.replace("?target=blank", ""));
                } /*else if (webExtensions.contains(extention)) {
                    mWebView.loadUrl(AppConstants.GOOGLE_DOCS_VIEWER + siteUrl);
                    mWebView.getSettings().setBuiltInZoomControls(true);
                }*/ else {
                    mWebView.loadUrl(siteUrl);
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            mWebListener.onNetworkError();
        }
    }

    public void loadHtmlPage(String htmlContent) {
        if (NetworkChangeReceiver.isNetworkConnected()) {
            mWebView.loadDataWithBaseURL(null, htmlContent, "text/html; charset=utf-8", "UTF-8", null);
        } else {
            mWebListener.onNetworkError();
        }
    }

    public void reloadWebpage() {
        mWebView.reload();
    }

    public Boolean canLoadBackPage() {
        return mWebView.canGoBack();
    }

    public void loadBackPage() {
        mWebView.goBack();
    }

    public String pageUrl() {
        return mWebView.getUrl();
    }

    public void openNativeApp(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            mActivity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initFileChooser() {
        if (PermissionHelper.isPermissionGranted(mContext, PermissionHelper.FILE_UPLOAD)) {
            Intent chooseImageIntent = FileChoserHelper.getPickFileIntent(mActivity);

            if (mFragment == null) {
                mActivity.startActivityForResult(chooseImageIntent, AppConstants.KEY_FILE_PICKER);
            } else {
                mFragment.startActivityForResult(chooseImageIntent, AppConstants.KEY_FILE_PICKER);
            }

        } else {
            PermissionHelper.requestUploadPermission(mActivity);
        }
    }

    public void uploadFile(Intent data, String filepath) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Uri[] results = null;
            if (filepath != null) {
                results = new Uri[]{Uri.fromFile(new File(filepath))};
            }

            if (results == null) {
                String dataString = data.getDataString();
                if (dataString != null) {
                    results = new Uri[]{Uri.parse(dataString)};
                }
            }

            if (mFilePathCallback != null) {
                mFilePathCallback.onReceiveValue(results);
                mFilePathCallback = null;
            }
        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            Uri result = data == null ? Uri.fromFile(new File(filepath)) : data.getData();

            if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        }
    }

    public void cancelUpload() {
        if (mFilePathCallback != null) {
            mFilePathCallback.onReceiveValue(null);
        }
        mFilePathCallback = null;
    }

    public void downloadFile() {
        if (PermissionHelper.isPermissionGranted(mContext, PermissionHelper.FILE_DOWNLOAD)) {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));

            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
            String fileName = downloadUrl.substring(downloadUrl.lastIndexOf('/') + 1);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
            DownloadManager dm = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
            dm.enqueue(request);
        } else {
            PermissionHelper.requestDownloadPermission(mActivity);
        }
    }
}
