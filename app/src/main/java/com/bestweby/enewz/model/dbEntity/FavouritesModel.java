package com.bestweby.enewz.model.dbEntity;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.bestweby.enewz.database.helpers.DaoHelper;

/**
 * Created by Md Sahidul Islam on 12-May-19.
 */

@Entity(tableName = DaoHelper.FAVOURITE_POST_TBL)
public class FavouritesModel implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DaoHelper.COLUMN_AUTO_ID)
    private int autoId;

    @ColumnInfo(name = DaoHelper.COLUMN_POST_ID)
    private int postId;

    @ColumnInfo(name = DaoHelper.COLUMN_POST_NAME)
    private String postName;

    @ColumnInfo(name = DaoHelper.COLUMN_POST_DATE)
    private String postDate;

    @ColumnInfo(name = DaoHelper.COLUMN_POST_CATEGORY)
    private String postCategory;

    @ColumnInfo(name = DaoHelper.COLUMN_POST_IMAGE)
    private String postImage;


    @Ignore
    public FavouritesModel() {
    }

    public FavouritesModel(int postId, String postName, String postDate, String postCategory, String postImage) {
        this.postId = postId;
        this.postName = postName;
        this.postDate = postDate;
        this.postCategory = postCategory;
        this.postImage = postImage;
    }

    protected FavouritesModel(Parcel in) {
        autoId = in.readInt();
        postId = in.readInt();
        postName = in.readString();
        postDate = in.readString();
        postCategory = in.readString();
        postImage = in.readString();
    }

    public static final Creator<FavouritesModel> CREATOR = new Creator<FavouritesModel>() {
        @Override
        public FavouritesModel createFromParcel(Parcel in) {
            return new FavouritesModel(in);
        }

        @Override
        public FavouritesModel[] newArray(int size) {
            return new FavouritesModel[size];
        }
    };

    public int getAutoId() {
        return autoId;
    }

    public void setAutoId(int autoId) {
        this.autoId = autoId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPostCategory() {
        return postCategory;
    }

    public void setPostCategory(String postCategory) {
        this.postCategory = postCategory;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(autoId);
        dest.writeInt(postId);
        dest.writeString(postName);
        dest.writeString(postDate);
        dest.writeString(postCategory);
        dest.writeString(postImage);
    }
}
