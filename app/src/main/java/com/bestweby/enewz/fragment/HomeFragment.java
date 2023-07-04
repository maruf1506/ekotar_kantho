package com.bestweby.enewz.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bestweby.enewz.R;
import com.bestweby.enewz.activity.PostDetailActivity;
import com.bestweby.enewz.adapter.recycler.NewsPostListAdapter;
import com.bestweby.enewz.adapter.viewpager.NewsTemplatePagerAdapter;
import com.bestweby.enewz.app.BaseActivity;
import com.bestweby.enewz.cache.constant.AppConstants;
import com.bestweby.enewz.helper.AppHelper;
import com.bestweby.enewz.listener.ItemViewClickListener;
import com.bestweby.enewz.listener.MenuItemClickListener;
import com.bestweby.enewz.model.posts.post.PostModel;
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


public class HomeFragment extends Fragment {

    private View view;
    private NewsPostListAdapter newsPostListAdapter;
    private RecyclerView recyclerView;
    private List<PostModel> postModelList;
    private SwipeRefreshLayout swipe_refresh;
    private LinearLayout shimmerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_home, container, false);

        swipe_refresh = view.findViewById(R.id.swipe_refresh);
        recyclerView = view.findViewById(R.id.category_posts_recyclerview);
        shimmerView = view.findViewById(R.id.shimmerView);

        initVars();
        initTablayout();
        initListener();
        loadCategories();

        return view;
    }

    private void initVars() {
        postModelList = new ArrayList<>();
    }

    private void initTablayout() {
        newsPostListAdapter = new NewsPostListAdapter(getActivity(), (ArrayList<PostModel>) postModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(newsPostListAdapter);
    }

    private void initListener() {
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        newsPostListAdapter.setItemClickListener(new ItemViewClickListener() {
            @Override
            public void onItemViewClickGetPosition(int position, View view) {
                switch (view.getId()) {
                    case R.id.parent_view:
                    case R.id.first_news:
                        Bundle bundle = new Bundle();
                        bundle.putInt(AppConstants.BUNDLE_POST_ID, postModelList.get(position).getId());
                        bundle.putString(AppConstants.BUNDLE_PAGE_TITLE, postModelList.get(position).getTitle().getRendered());
                        startActivity(new Intent(getActivity(), PostDetailActivity.class).putExtras(bundle));
                        break;
                }
            }
        });

        newsPostListAdapter.setMenuItemClickListener(new MenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_share:
                        ((BaseActivity) getActivity()).sharePost(postModelList.get(position));
                        break;
                    case R.id.menu_save:
                        ((BaseActivity) getActivity()).checkAndSaveFavourite(postModelList.get(position));
                        break;
                }
            }
        });
    }

    private void refreshData() {
        recyclerView.setAdapter(null);
        initTablayout();

        loadCategories();

        if (swipe_refresh.isRefreshing()) {
            swipe_refresh.setRefreshing(false);
        }
    }

    private void loadCategories() {
        if (NetworkChangeReceiver.isNetworkConnected()) {
            recyclerView.setVisibility(View.GONE);
            shimmerView.setVisibility(View.VISIBLE);

            HashMap<String, String> categoriesMap = ApiRequests.buildAllPosts(AppConstants.MAX_PER_PAGE);
            ApiClient.getInstance().getApiInterface().getPosts(categoriesMap).enqueue(new Callback<List<PostModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<PostModel>> call, @NonNull Response<List<PostModel>> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            postModelList.clear();

                            /* Show only Main/Parent Categories*/
                            for (PostModel postModel : response.body()) {
                                postModelList.add(postModel);
                            }

                            /* Show All Categories*/
                            //postModelList.addAll(response.body());

                            if (postModelList != null && postModelList.size() > 0) {
                                newsPostListAdapter.notifyDataSetChanged();
                            }

                            shimmerView.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        AppHelper.showShortToast(getActivity(), getString(R.string.failed_msg));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<PostModel>> call, @NonNull Throwable t) {
                    shimmerView.setVisibility(View.GONE);
                    //AppHelper.showShortToast(getActivity(), getActivity().getString(R.string.failed_msg));
                }
            });
        }
        AppHelper.noInternetWarning(getActivity(), view);
    }
}