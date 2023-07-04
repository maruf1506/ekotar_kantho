package com.bestweby.enewz.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingUtil;

import com.bestweby.enewz.R;
import com.bestweby.enewz.app.BaseActivity;
import com.bestweby.enewz.cache.constant.AppConstants;
import com.bestweby.enewz.cache.preference.AppPreference;
import com.bestweby.enewz.database.helpers.DaoHelper;
import com.bestweby.enewz.database.helpers.DbLoaderInterface;
import com.bestweby.enewz.database.loader.FavouriteItemLoader;
import com.bestweby.enewz.databinding.ActivityPostDetailLayoutBinding;
import com.bestweby.enewz.helper.AppHelper;
import com.bestweby.enewz.helper.DateHelper;
import com.bestweby.enewz.helper.WebEngine;
import com.bestweby.enewz.listener.WebProgressListener;
import com.bestweby.enewz.model.posts.post.PostModel;
import com.bestweby.enewz.model.posts.post.WpTerm;
import com.bestweby.enewz.network.ApiClient;
import com.bestweby.enewz.network.ApiRequests;
import com.bestweby.enewz.receiver.NetworkChangeReceiver;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Md Sahidul Islam on 02-Jun-19.
 */
public class PostDetailActivity extends BaseActivity{

    private static final int RECOVERY_REQUEST = 1;

    private ActivityPostDetailLayoutBinding binding;
    private WebEngine webEngine;

    private List<PostModel> relatedPosts;
    private PostModel postModel;
    private Boolean isFavourite = false;

    private String pageTitle;
    private boolean isfromNotification;
    private String videoUrl = "";
    private int postId = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_detail_layout);

        initVars();
        initWebEngine();
        loadPostDetail();
        initListener();
        checkFavourite();


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (AppPreference.getInstance(getApplicationContext()).getTextSize().equals(getResources().getString(R.string.small_text))) {
            binding.postDetailView.getSettings().setTextZoom(80);
        } else if (AppPreference.getInstance(getApplicationContext()).getTextSize().equals(getResources().getString(R.string.default_text))) {
            binding.postDetailView.getSettings().setTextZoom(100);
        } else if (AppPreference.getInstance(getApplicationContext()).getTextSize().equals(getResources().getString(R.string.large_text))) {
            binding.postDetailView.getSettings().setTextZoom(120);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (isfromNotification) {
            startActivity(new Intent(PostDetailActivity.this, NewsTemplateActivity.class));
            finish();
        } else if (webEngine.canLoadBackPage()) {
            webEngine.loadBackPage();
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.post_detail_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (isfromNotification)
                    startActivity(new Intent(PostDetailActivity.this, NewsTemplateActivity.class));
                finish();
                return true;
            case R.id.toolbar_settings:
                startActivity(new Intent(PostDetailActivity.this, AppSettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initVars() {
        relatedPosts = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            if (bundle.containsKey(AppConstants.BUNDLE_PAGE_TITLE))
                pageTitle = bundle.getString(AppConstants.BUNDLE_PAGE_TITLE);

            if (bundle.containsKey(AppConstants.BUNDLE_POST_ID))
                postId = bundle.getInt(AppConstants.BUNDLE_POST_ID);

            if (bundle.containsKey(AppConstants.BUNDLE_FROM_NOTIFICATION)) {
                isfromNotification = bundle.getBoolean(AppConstants.BUNDLE_FROM_NOTIFICATION, false);
            }
        }

        if (postId == 0) {
            finish();
            AppHelper.showShortToast(this, getString(R.string.failed_msg));
        }
    }

    private void initListener() {
        binding.detailToolbar.toolbarMenuSearch.setVisibility(View.GONE);

        binding.savePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFavourite();
            }
        });

        binding.sharePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharePost(postModel);
            }
        });

    }

    private void initWebEngine() {
        webEngine = new WebEngine(PostDetailActivity.this, binding.postDetailView);
        webEngine.initView();

        webEngine.initListener(new WebProgressListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onProgress(int progress) {
            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onNetworkError() {
                AppHelper.noInternetWarning(PostDetailActivity.this, binding.postDetailView);
            }
        });
    }

    private void initDetailView() {
        setSupportActionBar(binding.detailToolbar.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.postDetailView.getSettings();
        binding.postDetailView.setBackgroundColor(Color.TRANSPARENT);
        binding.postDetailView.setVerticalScrollBarEnabled(false);
        binding.postDetailView.setHorizontalScrollBarEnabled(false);

        binding.postDetailView.getSettings().setJavaScriptEnabled(true);
        binding.postDetailView.getSettings().setAppCacheEnabled(true);
        binding.postDetailView.getSettings().setLoadWithOverviewMode(true);

        if (postModel.getId() != null && postModel.getId() > 0) {
            binding.detailShimmerLayout.productDetailShimmerView.setVisibility(View.GONE);
            binding.postDetailLayout.setVisibility(View.VISIBLE);

            String postTitle = postModel.getTitle().getRendered();
            String postDetail = postModel.getContent().getRendered();
            String postDate = DateHelper.formateISODate(postModel.getDateGmt());
            String postCategory = "";
            videoUrl = AppHelper.getYoutubeId(postModel.getContent().getRendered());

            StringBuilder stringBuilder = new StringBuilder();

            if (postModel.getEmbedded().getWpTerm().get(0).size() > 0) {
                for (WpTerm wpTerm : postModel.getEmbedded().getWpTerm().get(0)) {
                    if (stringBuilder.length() > 0) stringBuilder.append(", ");
                    stringBuilder.append(wpTerm.getName());
                }
            }

            postCategory = stringBuilder.toString();

            if (videoUrl.isEmpty() && postModel.getEmbedded().getWpFeaturedmedia().size() > 0) {
                String postImage = postModel.getEmbedded().getWpFeaturedmedia().get(0).getSourceUrl();
                Picasso.get().load(postImage)
                        .placeholder(context.getResources().getDrawable(R.drawable.image_placeholder))
                        .error(context.getResources().getDrawable(R.drawable.image_placeholder))
                        .into(binding.postImage);
            } else {
                binding.postImage.setVisibility(View.GONE);
                binding.detailToolbar.toolbarTitle.setText(postCategory);
                setAppbarSettings();
            }

            if (postModel.getEmbedded().getAuthor().size() > 0) {
                String authorName = postModel.getEmbedded().getAuthor().get(0).getName();
                binding.postAuthorName.setText(authorName);
                binding.postAuthorLayout.setVisibility(View.VISIBLE);
            } else {
                binding.postAuthorLayout.setVisibility(View.GONE);
            }

            binding.postTitle.setText(AppHelper.fromHtml(postTitle));
            binding.postDate.setText(postDate);

            int themeValue = AppPreference.getInstance(this).getInteger(getResources().getString(R.string.pref_theme));
            String themeMode = getResources().getStringArray(R.array.app_theme_entries)[themeValue];

            if (themeMode.equals(getResources().getString(R.string.theme_light))) {
                postDetail = AppConstants.CSS_PROPERTIES_BLACK_FONT + postDetail;
            }

            if (themeMode.equals(getResources().getString(R.string.theme_dark))) {
                postDetail = AppConstants.CSS_PROPERTIES_WHITE_FONT + postDetail;
            }

            String layoutDirection = AppHelper.getAppLayoutDirection();
            postDetail = "<html><body dir=\"" + layoutDirection + "\">" + postDetail + "</body></html>";

            webEngine.loadHtmlPage(postDetail);

            // binding.postDetailView.loadDataWithBaseURL(null, postDetail, "text/html; charset=utf-8", "UTF-8", null);
        } else {
            binding.postDetailLayout.setVisibility(View.GONE);
            binding.detailShimmerLayout.productDetailShimmerView.setVisibility(View.VISIBLE);
            AppHelper.showShortToast(this, getString(R.string.failed_msg));
        }
    }

    private void loadPostDetail() {
        if (NetworkChangeReceiver.isNetworkConnected()) {
            HashMap<String, String> postDetailMap = ApiRequests.buildPostDetail();
            ApiClient.getInstance().getApiInterface().getPostDetails(postId, postDetailMap).enqueue(new Callback<PostModel>() {
                @Override
                public void onResponse(@NonNull Call<PostModel> call, @NonNull Response<PostModel> response) {
                    if (response.isSuccessful()) {
                        postModel = response.body();
                        initDetailView();
                    } else {
                        AppHelper.showShortToast(PostDetailActivity.this, getString(R.string.failed_msg));
                        finish();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<PostModel> call, @NonNull Throwable t) {
                    AppHelper.showShortToast(PostDetailActivity.this, getString(R.string.failed_msg));
                    finish();
                }
            });
        }
        AppHelper.noInternetWarning(context, binding.getRoot());
    }


    private void checkFavourite() {
        FavouriteItemLoader favouriteItemLoader = new FavouriteItemLoader(this);
        favouriteItemLoader.execute(DaoHelper.FETCH, postId);
        favouriteItemLoader.setDbLoaderInterface(new DbLoaderInterface() {
            @Override
            public void onFinished(Object object) {
                if (object != null) {
                    isFavourite = true;
                    binding.savePost.setImageResource(R.drawable.ic_bookmark_marked_128);
                } else {
                    isFavourite = false;
                    binding.savePost.setImageResource(R.drawable.ic_bookmark_unmarked_128);
                }
            }
        });
    }

    private void toggleFavourite() {
        FavouriteItemLoader favouriteItemLoader = new FavouriteItemLoader(this);
        if (isFavourite) {
            favouriteItemLoader.execute(DaoHelper.DELETE, postId);
        } else {
            savePost(postModel);
        }

        checkFavourite();
    }

    private void setAppbarSettings() {
        binding.appBarLayout.setLayoutParams(new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT));
        binding.appBarLayout.requestLayout();

        binding.detailToolbar.toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }
}
