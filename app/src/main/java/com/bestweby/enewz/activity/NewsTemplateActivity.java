package com.bestweby.enewz.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import com.bestweby.enewz.R;
import com.bestweby.enewz.app.BaseActivity;
import com.bestweby.enewz.databinding.ActivityNewsTemplateLayoutBinding;
import com.bestweby.enewz.databinding.NavHeaderLayoutBinding;
import com.bestweby.enewz.fragment.HomeFragment;
import com.bestweby.enewz.fragment.NewsFragment;
import com.bestweby.enewz.helper.AppHelper;

/**
 * Created by Md Sahidul Islam on 02-Jun-19.
 */

public class NewsTemplateActivity extends BaseActivity {

    private ActivityNewsTemplateLayoutBinding binding;
    private NavHeaderLayoutBinding navHeaderbinding;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private boolean doubleBackToExitPressedOnce = false;
    private int NUM_PAGES = 0, CURRENT_PAGE = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         sharedPreferences = getSharedPreferences("HOla", Context.MODE_PRIVATE);
         editor = sharedPreferences.edit();

        initView();
        initListener();
        intHomeNews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.mainNavView.getMenu().getItem(0).setChecked(true);
    }

    private void intHomeNews(){
        getSupportFragmentManager().beginTransaction().replace(R.id.homecontainer,new HomeFragment()).commit();
        editor.putInt("type",0);
        editor.commit();
        binding.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.homecontainer,new HomeFragment()).commit();
                editor.putInt("type",0);
                editor.commit();
            }
        });
        binding.newsfeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.homecontainer,new NewsFragment()).commit();
                editor.putInt("type",1);
                editor.commit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (this.binding.homeDrawerlayout.isDrawerOpen(GravityCompat.START)) {
            this.binding.homeDrawerlayout.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            AppHelper.showShortToast(NewsTemplateActivity.this, "Please click BACK again to exit");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    private void initView() {
        binding = DataBindingUtil.setContentView(NewsTemplateActivity.this, R.layout.activity_news_template_layout);
        initDrawerLayout(binding.homeDrawerlayout, binding.mainNavView, binding.homeToolbar.toolbar);
        initializeDrawerHeader();
    }

    private void initializeDrawerHeader() {
        navHeaderbinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.nav_header_layout, binding.mainNavView, false);
        binding.mainNavView.addHeaderView(navHeaderbinding.getRoot());
    }

    private void initListener() {
        binding.homeToolbar.toolbarMenuSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewsTemplateActivity.this, SearchPostActivity.class));
            }
        });
    }
}
