package com.bestweby.enewz.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bestweby.enewz.R;
import com.bestweby.enewz.adapter.viewpager.NewsTemplatePagerAdapter;
import com.bestweby.enewz.cache.constant.AppConstants;
import com.bestweby.enewz.helper.AppHelper;
import com.bestweby.enewz.model.Category.CategoryModel;
import com.bestweby.enewz.network.ApiClient;
import com.bestweby.enewz.network.ApiRequests;
import com.bestweby.enewz.receiver.NetworkChangeReceiver;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsFragment extends Fragment {

    private View view;
    private NewsTemplatePagerAdapter newsTemplatePagerAdapter;
    private List<CategoryModel> categoryList;
    private SwipeRefreshLayout swipe_refresh;
    private ViewPager categoryPostsViewpager;
    private TabLayout category_tablayout;
    private LinearLayout shimmerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_news, container, false);

        swipe_refresh = view.findViewById(R.id.swipe_refresh);
        categoryPostsViewpager = view.findViewById(R.id.category_posts_viewpager);
        category_tablayout = view.findViewById(R.id.category_tablayout);
        shimmerView = view.findViewById(R.id.shimmerView);


        initVars();
        initTablayout();
        initListener();
        loadCategories();

        return view;
    }

    private void initVars() {
        categoryList = new ArrayList<>();
    }

    private void initListener() {
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        categoryPostsViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                enableDisableSwipeRefresh(swipe_refresh, state == ViewPager.SCROLL_STATE_IDLE);
            }
        });
    }

    private void initTablayout() {
        newsTemplatePagerAdapter = new NewsTemplatePagerAdapter(getActivity().getSupportFragmentManager(), (ArrayList<CategoryModel>) categoryList);
        categoryPostsViewpager.setAdapter(newsTemplatePagerAdapter);
        category_tablayout.setupWithViewPager(categoryPostsViewpager);
    }

    private void refreshData() {
        categoryPostsViewpager.setAdapter(null);
        initTablayout();

        loadCategories();

        if (swipe_refresh.isRefreshing()) {
            swipe_refresh.setRefreshing(false);
        }
    }

    private void loadCategories() {
        if (NetworkChangeReceiver.isNetworkConnected()) {
            category_tablayout.setVisibility(View.GONE);
            categoryPostsViewpager.setVisibility(View.GONE);
            shimmerView.setVisibility(View.VISIBLE);

            HashMap<String, String> categoriesMap = ApiRequests.buildCategory(true, AppConstants.CATEGORY_PER_PAGE);
            ApiClient.getInstance().getApiInterface().getCategories(categoriesMap).enqueue(new Callback<List<CategoryModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<CategoryModel>> call, @NonNull Response<List<CategoryModel>> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            categoryList.clear();

                            /* Show only Main/Parent Categories*/
                            for (CategoryModel categoryModel : response.body()) {
                                if (categoryModel.getCount() > 0) {
                                    categoryList.add(categoryModel);
                                }
                            }

                            /* Show All Categories*/
                            // categoryList.addAll(response.body());

                            if (categoryList != null && categoryList.size() > 0) {
                                newsTemplatePagerAdapter.notifyDataSetChanged();

                                category_tablayout.setVisibility(View.VISIBLE);
                                categoryPostsViewpager.setVisibility(View.VISIBLE);
                            }

                            shimmerView.setVisibility(View.GONE);
                        }
                    } else {
                        AppHelper.showShortToast(getActivity(), getString(R.string.failed_msg));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<CategoryModel>> call, @NonNull Throwable t) {
                    shimmerView.setVisibility(View.GONE);
                    AppHelper.showShortToast(getActivity(), getString(R.string.failed_msg));
                }
            });
        }
        AppHelper.noInternetWarning(getActivity(), view);
    }

    private void enableDisableSwipeRefresh(SwipeRefreshLayout swipeRefreshLayout, boolean enable) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setEnabled(enable);
        }
    }
}