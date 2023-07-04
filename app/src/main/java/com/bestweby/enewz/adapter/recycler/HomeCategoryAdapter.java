package com.bestweby.enewz.adapter.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bestweby.enewz.R;
import com.bestweby.enewz.databinding.ItemHomeCategoryListLayoutBinding;
import com.bestweby.enewz.helper.AppHelper;
import com.bestweby.enewz.listener.ItemViewClickListener;
import com.bestweby.enewz.model.Category.CategoryModel;

import java.util.List;
import java.util.Random;

/**
 * Created by Md Sahidul Islam on 04-Jun-19.
 */
public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.HomeCategoryViewHolder> {

    private Context context;
    private List<CategoryModel> arrayList;

    private ItemViewClickListener itemClickListener;

    public HomeCategoryAdapter() {
    }

    public HomeCategoryAdapter(Context context, List<CategoryModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public void setItemClickListener(ItemViewClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public HomeCategoryAdapter.HomeCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new HomeCategoryViewHolder((ItemHomeCategoryListLayoutBinding) DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_home_category_list_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCategoryAdapter.HomeCategoryViewHolder holder, int position) {
        String categoryName = arrayList.get(position).getName();
        holder.binding.categoryName.setText(AppHelper.fromHtml(categoryName));

        Random rand = new Random();
        int num = rand.nextInt(7) + 1;

        switch (num) {
            case 1:
                holder.binding.categoryName.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_blue));
                break;
            case 2:
                holder.binding.categoryName.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_teal));
                break;
            case 3:
                holder.binding.categoryName.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_red));
                break;
            case 4:
                holder.binding.categoryName.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_green));
                break;
            case 5:
                holder.binding.categoryName.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_yellow));
                break;
            case 6:
                holder.binding.categoryName.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_orange));
                break;
            case 7:
                holder.binding.categoryName.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_violet));
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class HomeCategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemHomeCategoryListLayoutBinding binding;

        HomeCategoryViewHolder(@NonNull ItemHomeCategoryListLayoutBinding layoutBinding) {
            super(layoutBinding.getRoot());
            binding = layoutBinding;

            binding.categoryName.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null)
                itemClickListener.onItemViewClickGetPosition(getLayoutPosition(), view);
        }
    }
}
