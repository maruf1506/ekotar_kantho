package com.bestweby.enewz.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bestweby.enewz.R;
import com.bestweby.enewz.activity.PostDetailActivity;
import com.bestweby.enewz.adapter.recycler.NewsPostListAdapter;
import com.bestweby.enewz.app.BaseActivity;
import com.bestweby.enewz.cache.constant.AppConstants;
import com.bestweby.enewz.databinding.NewsPostListFragmentLayoutBinding;
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

import static com.bestweby.enewz.app.BaseActivity.setEmptyLayout;

/**
 * Created by Md Sahidul Islam on 03-Jun-19.
 */
public class NewsPostListFragment extends Fragment {

    private NewsPostListFragmentLayoutBinding binding;
    private Context context;

    private NewsPostListAdapter postListAdapter;

    private List<PostModel> postList;
    private int categoryId;
    private int pageNumber = AppConstants.PAGE_NUMBER;
    private Boolean isDataLoading = false;
    private Boolean canLoadMore = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();

        initVar();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.news_post_list_fragment_layout, container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initRecyclerLayout();
        initListener();

        loadCategoryPosts(AppConstants.PAGE_NUMBER);
    }

    private void initVar() {
        postList = new ArrayList<>();

        Bundle bundle = getArguments();
        if (bundle != null) {
            categoryId = getArguments().getInt(AppConstants.BUNDLE_CATEGORY_ID);
        }
    }

    private void initListener() {
        postListAdapter.setItemClickListener(new ItemViewClickListener() {
            @Override
            public void onItemViewClickGetPosition(int position, View view) {
                switch (view.getId()) {
                    case R.id.parent_view:
                        Bundle bundle = new Bundle();
                        bundle.putInt(AppConstants.BUNDLE_POST_ID, postList.get(position).getId());
                        bundle.putString(AppConstants.BUNDLE_PAGE_TITLE, postList.get(position).getTitle().getRendered());
                        startActivity(new Intent(getActivity(), PostDetailActivity.class).putExtras(bundle));
                        break;
                }
            }
        });

        postListAdapter.setMenuItemClickListener(new MenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_share:
                        ((BaseActivity) getActivity()).sharePost(postList.get(position));
                        break;
                    case R.id.menu_save:
                        ((BaseActivity) getActivity()).checkAndSaveFavourite(postList.get(position));
                        break;
                }
            }
        });

    }

    private void initRecyclerLayout() {
        postListAdapter = new NewsPostListAdapter(context, postList);
        binding.primaryRecycler.setLayoutManager(new LinearLayoutManager(context));
        binding.primaryRecycler.setItemAnimator(new DefaultItemAnimator());
        binding.primaryRecycler.setNestedScrollingEnabled(false);
        binding.primaryRecycler.setAdapter(postListAdapter);
        binding.nestedscrollView.setOnScrollChangeListener(onNestedScrollChange());
    }

    private void loadCategoryPosts(int pageNumber) {
        if (NetworkChangeReceiver.isNetworkConnected()) {
            if (pageNumber == AppConstants.PAGE_NUMBER) {
                binding.postRecyclerCardView.setVisibility(View.GONE);
                binding.emptyListLayout.setVisibility(View.GONE);
                binding.newsShimmerView.shimmerView.setVisibility(View.VISIBLE);
            }

            HashMap<String, String> categoryPostsMap = ApiRequests.buildCategoryPosts(categoryId, pageNumber);
            ApiClient.getInstance().getApiInterface().getPosts(categoryPostsMap).enqueue(new Callback<List<PostModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<PostModel>> call, @NonNull Response<List<PostModel>> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().size() > 0) {
                                canLoadMore = true;

                                postList.addAll(response.body());
                                postListAdapter.notifyDataSetChanged();
                                binding.postRecyclerCardView.setVisibility(View.VISIBLE);
                                binding.newsShimmerView.shimmerView.setVisibility(View.GONE);
                            } else {
                                if (postList.size() == 0) {
                                    binding.emptyListLayout.removeAllViews();
                                    binding.emptyListLayout.addView(setEmptyLayout(context, getString(R.string.no_data)));
                                    binding.emptyListLayout.setVisibility(View.VISIBLE);
                                    binding.newsShimmerView.shimmerView.setVisibility(View.GONE);
                                    binding.postRecyclerCardView.setVisibility(View.GONE);
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
                    binding.newsShimmerView.shimmerView.setVisibility(View.GONE);
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
                            loadCategoryPosts(pageNumber);
                        }
                    }
                }
            }
        };
    }
}
