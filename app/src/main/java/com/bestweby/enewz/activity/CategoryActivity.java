package com.bestweby.enewz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bestweby.enewz.R;
import com.bestweby.enewz.adapter.recycler.CategoryListAdapter;
import com.bestweby.enewz.app.BaseActivity;
import com.bestweby.enewz.cache.constant.AppConstants;
import com.bestweby.enewz.databinding.ActivityCategoryListLayoutBinding;
import com.bestweby.enewz.helper.AppHelper;
import com.bestweby.enewz.listener.ItemViewClickListener;
import com.bestweby.enewz.model.Category.CategoryModel;
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
 * Created by Md Sahidul Islam on 17-Jun-19.
 */
public class CategoryActivity extends BaseActivity {

    private ActivityCategoryListLayoutBinding binding;

    private CategoryListAdapter categoryListAdapter;
    private List<CategoryModel> categoryList, categoryArrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVars();
        initView();
        initRecyclerView();
        initListener();
        loadCategories();
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
        categoryList = new ArrayList<>();
        categoryArrayList = new ArrayList<>();
    }

    private void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category_list_layout);

        setToolbar(binding.primaryToolbar.toolbar, binding.primaryToolbar.toolbarTitle, getString(R.string.toolbar_categories));
    }

    private void initListener() {
        categoryListAdapter.setItemClickListener(new ItemViewClickListener() {
            @Override
            public void onItemViewClickGetPosition(int position, View view) {

                Bundle bundle = new Bundle();
                if (hasSubCategory(categoryList.get(position).getId())) {
                    bundle.putString(AppConstants.BUNDLE_PAGE_TITLE, categoryList.get(position).getName());
                    bundle.putInt(AppConstants.BUNDLE_CATEGORY_ID, categoryList.get(position).getId());
                    startActivity(new Intent(CategoryActivity.this, SubCategoryActivity.class).putExtras(bundle));
                } else {
                    bundle.putString(AppConstants.BUNDLE_PAGE_TITLE, categoryList.get(position).getName());
                    bundle.putInt(AppConstants.BUNDLE_CATEGORY_ID, categoryList.get(position).getId());
                    bundle.putSerializable(AppConstants.BUNDLE_LAYOUT_TYPE, AppConstants.ListLayoutType.LINEAR);
                    startActivity(new Intent(CategoryActivity.this, CategoryPostsActivity.class).putExtras(bundle));
                }
            }
        });
    }

    private void initRecyclerView() {
        categoryListAdapter = new CategoryListAdapter(CategoryActivity.this, categoryList);
        binding.categoryRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.categoryRecycler.setAdapter(categoryListAdapter);
    }

    private void loadCategories() {
        if (NetworkChangeReceiver.isNetworkConnected()) {
            binding.categoryRecycler.setVisibility(View.GONE);
            binding.categoryShimmerView.shimmerView.setVisibility(View.VISIBLE);

            HashMap<String, String> categoriesMap = ApiRequests.buildCategory(true, AppConstants.CATEGORY_PER_PAGE);
            ApiClient.getInstance().getApiInterface().getCategories(categoriesMap).enqueue(new Callback<List<CategoryModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<CategoryModel>> call, @NonNull Response<List<CategoryModel>> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            categoryList.clear();
                            categoryArrayList.clear();
                            categoryArrayList.addAll(response.body());

                            if (categoryArrayList != null && categoryArrayList.size() > 0) {
                                for (CategoryModel model : categoryArrayList) {
                                    if (model.getParent() == 0) {
                                        categoryList.add(model);
                                    }
                                }

                                categoryListAdapter.notifyDataSetChanged();
                                binding.categoryRecycler.setVisibility(View.VISIBLE);
                            }

                            binding.categoryShimmerView.shimmerView.setVisibility(View.GONE);
                        }
                    } else {
                        AppHelper.showShortToast(context, getString(R.string.failed_msg));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<CategoryModel>> call, @NonNull Throwable t) {
                    binding.categoryShimmerView.shimmerView.setVisibility(View.GONE);
                    AppHelper.showShortToast(context, getString(R.string.failed_msg));
                }
            });
        }
        AppHelper.noInternetWarning(context, binding.getRoot());
    }

    private boolean hasSubCategory(int categoryId) {

        boolean hasCategory = false;

        for (CategoryModel model : categoryArrayList) {
            if (categoryId > 0 && model.getParent() == categoryId) {
                hasCategory = true;
            }
        }

        return hasCategory;
    }
}
