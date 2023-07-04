package com.bestweby.enewz.adapter.recycler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bestweby.enewz.R;
import com.bestweby.enewz.databinding.ItemCategoryLinearPostListLayoutBinding;
import com.bestweby.enewz.helper.AppHelper;
import com.bestweby.enewz.helper.DateHelper;
import com.bestweby.enewz.listener.ItemViewClickListener;
import com.bestweby.enewz.listener.MenuItemClickListener;
import com.bestweby.enewz.model.posts.post.PostModel;
import com.bestweby.enewz.model.posts.post.Reply;
import com.bestweby.enewz.network.ApiClient;
import com.bestweby.enewz.network.ApiConfig;
import com.bestweby.enewz.network.ApiRequests;
import com.bestweby.enewz.receiver.NetworkChangeReceiver;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sahidul Islam on 19-Oct-19.
 */
public class CategoryLinearPostListAdapter extends RecyclerView.Adapter<CategoryLinearPostListAdapter.CategoryLinearPostListViewHolder> {

    private Context context;
    private List<PostModel> arrayList;

    private ItemViewClickListener itemClickListener;
    private MenuItemClickListener menuItemClickListener;

    public CategoryLinearPostListAdapter() {
    }

    public CategoryLinearPostListAdapter(Context context, List<PostModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public void setItemClickListener(ItemViewClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setMenuItemClickListener(MenuItemClickListener menuItemClickListener) {
        this.menuItemClickListener = menuItemClickListener;
    }

    @NonNull
    @Override
    public CategoryLinearPostListAdapter.CategoryLinearPostListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new CategoryLinearPostListAdapter.CategoryLinearPostListViewHolder((ItemCategoryLinearPostListLayoutBinding) DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_category_linear_post_list_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryLinearPostListAdapter.CategoryLinearPostListViewHolder holder, final int position) {
        String postTitle = arrayList.get(position).getTitle().getRendered();
        String postDate = DateHelper.formateISODate(arrayList.get(position).getDateGmt());

        if (arrayList.get(position).getEmbedded().getWpFeaturedmedia().size() > 0) {
            String postImage = arrayList.get(position).getEmbedded().getWpFeaturedmedia().get(0).getSourceUrl();
            Picasso.get().load(postImage)
                    .placeholder(context.getResources().getDrawable(R.drawable.image_placeholder))
                    .error(context.getResources().getDrawable(R.drawable.image_placeholder))
                    .into(holder.binding.postImage);
        }

        holder.binding.postTitle.setText(AppHelper.fromHtml(postTitle));
        holder.binding.postDate.setText(postDate);

        holder.binding.postMenu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                @SuppressLint("RestrictedApi") MenuBuilder menuBuilder = new MenuBuilder(context);
                MenuInflater inflater = new MenuInflater(context);
                Context wrapper = new ContextThemeWrapper(context, R.style.PopupMenu);
                inflater.inflate(R.menu.recycler_item_menu, menuBuilder);
                @SuppressLint("RestrictedApi") MenuPopupHelper optionsMenu = new MenuPopupHelper(wrapper, menuBuilder, view);
                optionsMenu.setForceShowIcon(true);

                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                        if (menuItemClickListener != null)
                            menuItemClickListener.onMenuItemClick(position, item);
                        return true;
                    }

                    @Override
                    public void onMenuModeChange(MenuBuilder menu) {
                    }
                });
                optionsMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class CategoryLinearPostListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemCategoryLinearPostListLayoutBinding binding;

        CategoryLinearPostListViewHolder(@NonNull ItemCategoryLinearPostListLayoutBinding layoutBinding) {
            super(layoutBinding.getRoot());
            binding = layoutBinding;

            binding.parentView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null)
                itemClickListener.onItemViewClickGetPosition(getLayoutPosition(), view);
        }
    }
}
