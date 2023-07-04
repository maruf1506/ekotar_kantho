package com.bestweby.enewz.adapter.recycler;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bestweby.enewz.R;
import com.bestweby.enewz.databinding.ItemCategoryListLayoutBinding;
import com.bestweby.enewz.helper.AppHelper;
import com.bestweby.enewz.listener.ItemViewClickListener;
import com.bestweby.enewz.model.Category.CategoryModel;

import java.util.List;

/**
 * Created by Md Sahidul Islam on 17-Jun-19.
 */
public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryListViewHolder> {

    private Context context;
    private List<CategoryModel> arrayList;

    private ItemViewClickListener itemClickListener;

    public CategoryListAdapter() {
    }

    public CategoryListAdapter(Context context, List<CategoryModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public void setItemClickListener(ItemViewClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public CategoryListAdapter.CategoryListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new CategoryListViewHolder((ItemCategoryListLayoutBinding) DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_category_list_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryListAdapter.CategoryListViewHolder holder, int position) {
        String categoryTitle = arrayList.get(position).getName();
        int postCount = arrayList.get(position).getCount();
        String categorySign = String.valueOf(categoryTitle.charAt(0));

        holder.binding.categoryTitle.setText(AppHelper.fromHtml(categoryTitle));
        holder.binding.categoryPostCount.setText(postCount + " " + context.getResources().getString(R.string.posts));

        if (postCount > 0) {
            holder.binding.subCategorySign.setVisibility(View.GONE);
        } else {
            holder.binding.subCategorySign.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CategoryListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemCategoryListLayoutBinding binding;

        public CategoryListViewHolder(@NonNull ItemCategoryListLayoutBinding layoutBinding) {
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
