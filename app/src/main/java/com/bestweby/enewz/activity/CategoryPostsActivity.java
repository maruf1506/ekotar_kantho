package com.bestweby.enewz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bestweby.enewz.R;
import com.bestweby.enewz.adapter.recycler.CategoryLinearPostListAdapter;
import com.bestweby.enewz.app.BaseActivity;
import com.bestweby.enewz.cache.constant.AppConstants;
import com.bestweby.enewz.databinding.ActivityCategoryPostLayoutBinding;
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
 * Created by Md Sahidul Islam on 05-Jun-19.
 */
public class CategoryPostsActivity extends BaseActivity {

    private ActivityCategoryPostLayoutBinding binding;

    //private CategoryGridPostListAdapter gridPostListAdapter;
    private CategoryLinearPostListAdapter linearPostListAdapter;

    private List<PostModel> postList;
    private String pageTitle;
    private int categoryId;
    private Enum layoutType;

    private int pageNumber = AppConstants.PAGE_NUMBER;
    private Boolean isDataLoading = false;
    private Boolean canLoadMore = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVars();
        initView();
        initListener();
        initRecyclerView();
        loadCategoryPosts(AppConstants.PAGE_NUMBER, true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initVars() {
        postList = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            if (bundle.containsKey(AppConstants.BUNDLE_PAGE_TITLE))
                pageTitle = bundle.getString(AppConstants.BUNDLE_PAGE_TITLE);

            if (bundle.containsKey(AppConstants.BUNDLE_CATEGORY_ID))
                categoryId = bundle.getInt(AppConstants.BUNDLE_CATEGORY_ID);

            if (bundle.containsKey(AppConstants.BUNDLE_LAYOUT_TYPE))
                layoutType = (AppConstants.ListLayoutType) bundle.getSerializable(AppConstants.BUNDLE_LAYOUT_TYPE);
        }
    }

    private void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category_post_layout);

        setToolbar(binding.primaryToolbar.toolbar, binding.primaryToolbar.toolbarTitle, AppHelper.fromHtml(pageTitle));
    }

    private void initListener() {
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    private void initRecyclerView() {
       /* if (layoutType.equals(AppConstants.ListLayoutType.GRID)) {
            gridPostListAdapter = new CategoryGridPostListAdapter(CategoryPostsActivity.this, postList);
            binding.postRecycler.setLayoutManager(new GridLayoutManager(CategoryPostsActivity.this, 2));
            binding.postRecycler.setItemAnimator(new DefaultItemAnimator());
            binding.postRecycler.setNestedScrollingEnabled(false);
            binding.postRecycler.setAdapter(gridPostListAdapter);

            gridPostListAdapter.setItemClickListener(new ItemViewClickListener() {
                @Override
                public void onItemViewClickGetPosition(int position, View view) {
                    switch (view.getId()) {
                        case R.id.parent_view:
                            Bundle bundle = new Bundle();
                            bundle.putInt(AppConstants.BUNDLE_POST_ID, postList.get(position).getId());
                            bundle.putString(AppConstants.BUNDLE_PAGE_TITLE, postList.get(position).getTitle().getRendered());
                            startActivity(new Intent(CategoryPostsActivity.this, PostDetailActivity.class).putExtras(bundle));
                            break;
                    }
                }
            });

            gridPostListAdapter.setMenuItemClickListener(new MenuItemClickListener() {
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
        }*/

        if (layoutType.equals(AppConstants.ListLayoutType.LINEAR)) {
            linearPostListAdapter = new CategoryLinearPostListAdapter(CategoryPostsActivity.this, postList);
            binding.postRecycler.setLayoutManager(new LinearLayoutManager(CategoryPostsActivity.this));
            binding.postRecycler.setItemAnimator(new DefaultItemAnimator());
            binding.postRecycler.setNestedScrollingEnabled(false);
            binding.postRecycler.setAdapter(linearPostListAdapter);


            linearPostListAdapter.setItemClickListener(new ItemViewClickListener() {
                @Override
                public void onItemViewClickGetPosition(int position, View view) {
                    switch (view.getId()) {
                        case R.id.parent_view:
                            Bundle bundle = new Bundle();
                            bundle.putInt(AppConstants.BUNDLE_POST_ID, postList.get(position).getId());
                            bundle.putString(AppConstants.BUNDLE_PAGE_TITLE, postList.get(position).getTitle().getRendered());
                            startActivity(new Intent(CategoryPostsActivity.this, PostDetailActivity.class).putExtras(bundle));
                            break;
                    }
                }
            });

            linearPostListAdapter.setMenuItemClickListener(new MenuItemClickListener() {
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

        binding.nestedscrollView.setOnScrollChangeListener(onNestedScrollChange());
    }

    private void refreshData() {
        loadCategoryPosts(AppConstants.PAGE_NUMBER, true);

        if (binding.swipeRefresh.isRefreshing()) {
            binding.swipeRefresh.setRefreshing(false);
        }
    }

    private void loadCategoryPosts(int pageNumber, final Boolean isFirstTimeLoad) {
        if (NetworkChangeReceiver.isNetworkConnected()) {
            if (pageNumber == AppConstants.PAGE_NUMBER) {
                binding.postRecycler.setVisibility(View.GONE);
                binding.emptyListLayout.setVisibility(View.GONE);

                if (layoutType.equals(AppConstants.ListLayoutType.GRID))
                    binding.gridShimmerView.shimmerView.setVisibility(View.VISIBLE);

                if (layoutType.equals(AppConstants.ListLayoutType.LINEAR))
                    binding.linearShimmerView.shimmerView.setVisibility(View.VISIBLE);
            }

            HashMap<String, String> allPostsMap = ApiRequests.buildCategoryPosts(categoryId, pageNumber);
            ApiClient.getInstance().getApiInterface().getPosts(allPostsMap).enqueue(new Callback<List<PostModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<PostModel>> call, @NonNull Response<List<PostModel>> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().size() > 0) {
                                canLoadMore = true;

                                if (isFirstTimeLoad) postList.clear();
                                postList.addAll(response.body());
                                /*if (layoutType.equals(AppConstants.ListLayoutType.GRID)) {
                                    gridPostListAdapter.notifyDataSetChanged();
                                }*/

                                if (layoutType.equals(AppConstants.ListLayoutType.LINEAR)) {
                                    linearPostListAdapter.notifyDataSetChanged();
                                }

                                binding.postRecycler.setVisibility(View.VISIBLE);
                                binding.gridShimmerView.shimmerView.setVisibility(View.GONE);
                                binding.linearShimmerView.shimmerView.setVisibility(View.GONE);
                            } else {
                                if (postList.size() == 0) {
                                    binding.emptyListLayout.removeAllViews();
                                    binding.emptyListLayout.addView(setEmptyLayout(CategoryPostsActivity.this, getString(R.string.no_data)));
                                    binding.emptyListLayout.setVisibility(View.VISIBLE);

                                    binding.gridShimmerView.shimmerView.setVisibility(View.GONE);
                                    binding.linearShimmerView.shimmerView.setVisibility(View.GONE);
                                    binding.postRecycler.setVisibility(View.GONE);
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
                    binding.gridShimmerView.shimmerView.setVisibility(View.GONE);
                    binding.linearShimmerView.shimmerView.setVisibility(View.GONE);
                    AppHelper.showShortToast(context, getString(R.string.failed_msg));


                }
            });
        }
        AppHelper.noInternetWarning(context, binding.getRoot());
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
                            loadCategoryPosts(pageNumber, false);
                        }
                    }
                }
            }
        };
    }
}
