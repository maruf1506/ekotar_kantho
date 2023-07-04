package com.bestweby.enewz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bestweby.enewz.R;
import com.bestweby.enewz.adapter.recycler.SearchPostListAdapter;
import com.bestweby.enewz.app.BaseActivity;
import com.bestweby.enewz.cache.constant.AppConstants;
import com.bestweby.enewz.databinding.ActivitySearchPostLayoutBinding;
import com.bestweby.enewz.helper.AppHelper;
import com.bestweby.enewz.listener.ItemViewClickListener;
import com.bestweby.enewz.listener.MenuItemClickListener;
import com.bestweby.enewz.model.posts.post.PostModel;
import com.bestweby.enewz.network.ApiClient;
import com.bestweby.enewz.network.ApiRequests;
import com.bestweby.enewz.receiver.NetworkChangeReceiver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Md Sahidul Islam on 07-Jun-19.
 */
public class SearchPostActivity extends BaseActivity {

    private ActivitySearchPostLayoutBinding binding;

    private SearchPostListAdapter postListAdapter;
    private List<PostModel> postList;

    private int pageNumber = AppConstants.PAGE_NUMBER;
    private Boolean isDataLoading = false;
    private Boolean canLoadMore = false;
    private String searchKeyword = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVars();
        initView();
        initListener();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                AppHelper.hideKeyboard(SearchPostActivity.this);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initVars() {
        postList = new ArrayList<>();
    }

    private void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_post_layout);

        setSupportActionBar(binding.searchToolbar.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppHelper.showKeyboard(SearchPostActivity.this);
        initRecyclerView();

    }

    private void initListener() {
        binding.searchToolbar.searchTerm.addTextChangedListener(textChangeListener());
        binding.searchToolbar.clearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.searchToolbar.searchTerm.getText().clear();
                if (postList.size() > 0) {
                    searchKeyword = "";
                }
            }
        });

        postListAdapter.setItemClickListener(new ItemViewClickListener() {
            @Override
            public void onItemViewClickGetPosition(int position, View view) {
                switch (view.getId()) {
                    case R.id.parent_view:
                        Bundle bundle = new Bundle();
                        bundle.putInt(AppConstants.BUNDLE_POST_ID, postList.get(position).getId());
                        bundle.putString(AppConstants.BUNDLE_PAGE_TITLE, postList.get(position).getTitle().getRendered());
                        startActivity(new Intent(SearchPostActivity.this, PostDetailActivity.class).putExtras(bundle));
                        break;
                }
            }
        });

        postListAdapter.setMenuItemClickListener(new MenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_share:
                        sharePost(postList.get(position));
                        break;
                    case R.id.menu_save:
                        checkAndSaveFavourite(postList.get(position));
                        break;
                }
            }
        });

    }

    private void initRecyclerView() {
        postListAdapter = new SearchPostListAdapter(SearchPostActivity.this, postList);
        binding.primaryRecycler.setLayoutManager(new LinearLayoutManager(SearchPostActivity.this));
        binding.primaryRecycler.setItemAnimator(new DefaultItemAnimator());
        binding.primaryRecycler.setNestedScrollingEnabled(false);
        binding.primaryRecycler.setAdapter(postListAdapter);
        binding.nestedscrollView.setOnScrollChangeListener(onNestedScrollChange());
    }

    private void loadPosts(int pageNumber) {
        if (NetworkChangeReceiver.isNetworkConnected()) {
            if (pageNumber == AppConstants.PAGE_NUMBER) {
                binding.primaryRecycler.setVisibility(View.GONE);
                binding.emptyListLayout.setVisibility(View.GONE);
                binding.listShimmerLayout.shimmerView.setVisibility(View.VISIBLE);
            }

            HashMap<String, String> searchPostsMap = ApiRequests.buildSearchPosts(pageNumber, searchKeyword);
            ApiClient.getInstance().getApiInterface().getPosts(searchPostsMap).enqueue(new Callback<List<PostModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<PostModel>> call, @NonNull Response<List<PostModel>> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().size() > 0) {
                                canLoadMore = true;

                                postList.addAll(response.body());
                                postListAdapter.notifyDataSetChanged();
                                binding.primaryRecycler.setVisibility(View.VISIBLE);
                                binding.listShimmerLayout.shimmerView.setVisibility(View.GONE);
                            } else {
                                if (postList.size() == 0) {
                                    binding.emptyListLayout.removeAllViews();
                                    binding.emptyListLayout.addView(setEmptyLayout(SearchPostActivity.this, getString(R.string.no_data)));
                                    binding.emptyListLayout.setVisibility(View.VISIBLE);
                                    binding.listShimmerLayout.shimmerView.setVisibility(View.GONE);
                                    binding.primaryRecycler.setVisibility(View.GONE);
                                }
                                canLoadMore = false;
                            }
                            isDataLoading = false;
                            binding.progressBar.setVisibility(View.GONE);
                        }
                    } else {
                        if (response.code() == AppConstants.EMPTY_RESULT) {
                            canLoadMore = false;
                            binding.progressBar.setVisibility(View.GONE);
                        } else {
                            AppHelper.showShortToast(context, getString(R.string.failed_msg));
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<PostModel>> call, @NonNull Throwable t) {
                    binding.listShimmerLayout.shimmerView.setVisibility(View.GONE);
                    AppHelper.showShortToast(context, getString(R.string.failed_msg));
                }
            });
        }
        AppHelper.noInternetWarning(context, binding.getRoot());
    }

    private TextWatcher textChangeListener() {
        return new TextWatcher() {
            Handler handler = new Handler();
            Runnable runnable;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                handler.removeCallbacks(runnable);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                final String searchTerm = editable.toString().trim();

                if (!searchTerm.isEmpty())
                    binding.searchToolbar.clearSearch.setVisibility(View.VISIBLE);
                else binding.searchToolbar.clearSearch.setVisibility(View.GONE);

                runnable = new Runnable() {
                    @Override
                    public void run() {
                        if (!searchTerm.isEmpty()) {
                            searchKeyword = searchTerm;
                            postList.clear();
                            loadPosts(AppConstants.PAGE_NUMBER);
                            AppHelper.hideKeyboard(SearchPostActivity.this);
                        } else {
                            searchKeyword = "";
                        }
                    }
                };
                handler.postDelayed(runnable, 1000);
            }
        };
    }

    private NestedScrollView.OnScrollChangeListener onNestedScrollChange() {
        return new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView scrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY > 0) {
                    if (!scrollView.canScrollVertically(NestedScrollView.FOCUS_DOWN)) {
                        if (canLoadMore && !isDataLoading) {
                            isDataLoading = true;
                            binding.progressBar.setVisibility(View.VISIBLE);
                            pageNumber += 1;
                            loadPosts(pageNumber);
                        }
                    }
                }
            }
        };
    }
}
