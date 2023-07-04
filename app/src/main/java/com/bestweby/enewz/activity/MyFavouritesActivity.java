package com.bestweby.enewz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bestweby.enewz.R;
import com.bestweby.enewz.adapter.recycler.MyFavouritesAdapter;
import com.bestweby.enewz.app.BaseActivity;
import com.bestweby.enewz.cache.constant.AppConstants;
import com.bestweby.enewz.database.helpers.DaoHelper;
import com.bestweby.enewz.database.helpers.DbLoaderInterface;
import com.bestweby.enewz.database.loader.FavouriteItemLoader;
import com.bestweby.enewz.databinding.ActivityMyFavouritesLayoutBinding;
import com.bestweby.enewz.listener.ItemViewClickListener;
import com.bestweby.enewz.model.dbEntity.FavouritesModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Md Sahidul Islam on 14-May-19.
 */

public class MyFavouritesActivity extends BaseActivity {

    ActivityMyFavouritesLayoutBinding binding;

    MyFavouritesAdapter myFavouritesAdapter;
    private List<FavouritesModel> favouriteList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVars();
        initView();
        initRecyclerView();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadFavourites();
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
        favouriteList = new ArrayList<>();
    }

    private void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_favourites_layout);

        setToolbar(binding.primaryToolbar.toolbar, binding.primaryToolbar.toolbarTitle, getString(R.string.toolbar_bookmarks));
    }

    private void initListener() {
        myFavouritesAdapter.setItemClickListener(new ItemViewClickListener() {
            @Override
            public void onItemViewClickGetPosition(int position, View view) {
                switch (view.getId()) {
                    case R.id.parent_view:
                        Bundle bundle = new Bundle();
                        bundle.putString(AppConstants.BUNDLE_PAGE_TITLE, favouriteList.get(position).getPostName());
                        bundle.putInt(AppConstants.BUNDLE_POST_ID, favouriteList.get(position).getPostId());
                        startActivity(new Intent(MyFavouritesActivity.this, PostDetailActivity.class).putExtras(bundle));
                        break;
                    case R.id.remove_product:
                        removeFavourite(position);
                        break;
                }
            }
        });
    }

    private void initRecyclerView() {
        myFavouritesAdapter = new MyFavouritesAdapter(MyFavouritesActivity.this, favouriteList);
        binding.myFavouritesRecycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        binding.myFavouritesRecycler.setAdapter(myFavouritesAdapter);
    }

    private void loadFavourites() {
        FavouriteItemLoader favouriteItemLoader = new FavouriteItemLoader(this);
        favouriteItemLoader.execute(DaoHelper.FETCH_ALL);
        favouriteItemLoader.setDbLoaderInterface(new DbLoaderInterface() {
            @Override
            public void onFinished(Object object) {
                if (object != null) {
                    List<FavouritesModel> modelList = (List<FavouritesModel>) object;

                    if (modelList.size() > 0) {
                        favouriteList.clear();
                        favouriteList.addAll(modelList);
                        myFavouritesAdapter.notifyDataSetChanged();
                        binding.emptyListLayout.setVisibility(View.GONE);
                        binding.myFavouritesRecycler.setVisibility(View.VISIBLE);
                    } else {
                        binding.emptyListLayout.removeAllViews();
                        binding.emptyListLayout.addView(setEmptyLayout(MyFavouritesActivity.this, getString(R.string.no_bookmarks)));
                        binding.myFavouritesRecycler.setVisibility(View.GONE);
                        binding.emptyListLayout.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    private void removeFavourite(int position) {
        FavouritesModel model = favouriteList.get(position);
        FavouriteItemLoader favouriteItemLoader = new FavouriteItemLoader(this);
        favouriteItemLoader.execute(DaoHelper.DELETE, model.getPostId());

        favouriteList.remove(position);
        myFavouritesAdapter.notifyItemRemoved(position);

        loadFavourites();
    }
}
