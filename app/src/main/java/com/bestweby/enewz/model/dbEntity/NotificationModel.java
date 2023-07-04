package com.bestweby.enewz.model.dbEntity;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.bestweby.enewz.database.helpers.DaoHelper;

/**
 * Created by Md Sahidul Islam on 30-May-19.
 */

@Entity(tableName = DaoHelper.NOTIFICATION_TBL)
public class NotificationModel implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DaoHelper.COLUMN_AUTO_ID)
    private int autoId;

    @ColumnInfo(name = DaoHelper.COLUMN_NOTIFICATION_TITLE)
    private String title;

    @ColumnInfo(name = DaoHelper.COLUMN_NOTIFICATION_MESSAGE)
    private String message;

    @ColumnInfo(name = DaoHelper.COLUMN_NOTIFICATION_IMAGE)
    private String image;

    @ColumnInfo(name = DaoHelper.COLUMN_NOTIFICATION_URL)
    private String webUrl;

    @ColumnInfo(name = DaoHelper.COLUMN_NOTIFICATION_POST)
    private int postId;

    @ColumnInfo(name = DaoHelper.COLUMN_NOTIFICATION_TYPE)
    private String type;

    @ColumnInfo(name = DaoHelper.COLUMN_NOTIFICATION_SEEN)
    private Boolean isRead;


    @Ignore
    public NotificationModel() {
    }

    public NotificationModel(String title, String message, String image, String webUrl, int postId, String type, Boolean isRead) {

        this.title = title;
        this.message = message;
        this.image = image;
        this.webUrl = webUrl;
        this.postId = postId;
        this.type = type;
        this.isRead = isRead;
    }

    protected NotificationModel(Parcel in) {
        autoId = in.readInt();
        title = in.readString();
        message = in.readString();
        image = in.readString();
        webUrl = in.readString();
        postId = in.readInt();
        type = in.readString();
        byte tmpIsRead = in.readByte();
        isRead = tmpIsRead == 0 ? null : tmpIsRead == 1;
    }

    public static final Creator<NotificationModel> CREATOR = new Creator<NotificationModel>() {
        @Override
        public NotificationModel createFromParcel(Parcel in) {
            return new NotificationModel(in);
        }

        @Override
        public NotificationModel[] newArray(int size) {
            return new NotificationModel[size];
        }
    };

    public int getAutoId() {
        return autoId;
    }

    public void setAutoId(int autoId) {
        this.autoId = autoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(autoId);
        dest.writeString(title);
        dest.writeString(message);
        dest.writeString(image);
        dest.writeString(webUrl);
        dest.writeInt(postId);
        dest.writeString(type);
        dest.writeByte((byte) (isRead == null ? 0 : isRead ? 1 : 2));
    }
}
