package com.bestweby.enewz.adapter.viewpager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.bestweby.enewz.cache.constant.AppConstants;
import com.bestweby.enewz.fragment.NewsPostListFragment;
import com.bestweby.enewz.helper.AppHelper;
import com.bestweby.enewz.model.Category.CategoryModel;

import java.util.ArrayList;

/**
 * Created by Md Sahidul Islam on 03-Jun-19.
 */
public class NewsTemplatePagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<CategoryModel> categoryList;

    public NewsTemplatePagerAdapter(FragmentManager fm, ArrayList<CategoryModel> categoryModels) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        categoryList = categoryModels;
    }

    @Override
    public Fragment getItem(int i) {
        int categoryId = categoryList.get(i).getId();

        Fragment postListFragment = new NewsPostListFragment();
        Bundle args = new Bundle();
        args.putInt(AppConstants.BUNDLE_CATEGORY_ID, categoryId);
        postListFragment.setArguments(args);
        return postListFragment;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return AppHelper.fromHtml(categoryList.get(position).getName());
    }
}
