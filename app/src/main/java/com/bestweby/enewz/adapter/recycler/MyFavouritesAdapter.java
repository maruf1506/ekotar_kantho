package com.bestweby.enewz.adapter.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bestweby.enewz.R;
import com.bestweby.enewz.databinding.ItemFavouritePostLayoutBinding;
import com.bestweby.enewz.helper.DateHelper;
import com.bestweby.enewz.listener.ItemViewClickListener;
import com.bestweby.enewz.model.dbEntity.FavouritesModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Md Sahidul Islam on 14-May-19.
 */

public class MyFavouritesAdapter extends RecyclerView.Adapter<MyFavouritesAdapter.MyFavouritesViewHolder> {

    private Context context;
    private List<FavouritesModel> arrayList;

    private ItemViewClickListener itemClickListener;

    public MyFavouritesAdapter() {
    }

    public MyFavouritesAdapter(Context context, List<FavouritesModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public void setItemClickListener(ItemViewClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyFavouritesAdapter.MyFavouritesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new MyFavouritesViewHolder((ItemFavouritePostLayoutBinding) DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_favourite_post_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyFavouritesAdapter.MyFavouritesViewHolder holder, int position) {
        String postTitle = arrayList.get(position).getPostName();
        String postImage = arrayList.get(position).getPostImage();
        String postCategory = arrayList.get(position).getPostCategory();
        String postDate = DateHelper.formateISODate(arrayList.get(position).getPostDate());

        if (postImage != null) {
            Picasso.get().load(postImage)
                    .placeholder(context.getResources().getDrawable(R.drawable.image_placeholder))
                    .error(context.getResources().getDrawable(R.drawable.image_placeholder))
                    .into(holder.binding.postImage);
        }

        holder.binding.postTitle.setText(postTitle);
        holder.binding.postCategory.setText(postCategory);
        holder.binding.postDate.setText(postDate);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyFavouritesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemFavouritePostLayoutBinding binding;

        MyFavouritesViewHolder(@NonNull ItemFavouritePostLayoutBinding layoutBinding) {
            super(layoutBinding.getRoot());
            binding = layoutBinding;

            binding.parentView.setOnClickListener(this);
            binding.removeProduct.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null)
                itemClickListener.onItemViewClickGetPosition(getLayoutPosition(), view);
        }
    }
}
