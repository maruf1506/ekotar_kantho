package com.bestweby.enewz.database.helpers;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.bestweby.enewz.database.dao.FavouritesDao;
import com.bestweby.enewz.database.dao.NotificationDao;
import com.bestweby.enewz.model.dbEntity.FavouritesModel;
import com.bestweby.enewz.model.dbEntity.NotificationModel;


@Database(entities = {FavouritesModel.class, NotificationModel.class}, version = 1, exportSchema = false)
public abstract class DaoHelper extends RoomDatabase {

    /* Databse Name*/
    static final String DATABASE_NAME = "wp-news";

    /* Table Name*/
    public static final String FAVOURITE_POST_TBL = "favourite_posts_tbl";
    public static final String NOTIFICATION_TBL = "notification_tbl";


    /* Column Name*/
    public static final String COLUMN_AUTO_ID = "auto_id";
    public static final String COLUMN_POST_ID = "post_id";
    public static final String COLUMN_POST_NAME = "post_name";
    public static final String COLUMN_POST_DATE = "post_date";
    public static final String COLUMN_POST_CATEGORY = "post_category";
    public static final String COLUMN_POST_IMAGE = "post_image";
    public static final String COLUMN_NOTIFICATION_TITLE = "notification_title";
    public static final String COLUMN_NOTIFICATION_MESSAGE = "notification_message";
    public static final String COLUMN_NOTIFICATION_IMAGE = "notification_image";
    public static final String COLUMN_NOTIFICATION_URL = "notification_url";
    public static final String COLUMN_NOTIFICATION_POST = "notification_post";
    public static final String COLUMN_NOTIFICATION_TYPE = "notification_type";
    public static final String COLUMN_NOTIFICATION_SEEN = "notification_seen";


    // Query
    public static final int INSERT = 1, FETCH = 2, UPDATE = 3, DELETE = 4,
            SEARCH_KEY = 5, INSERT_ALL = 6, FETCH_ALL = 7, UPDATE_ALL = 8, DELETE_ALL = 9, FETCH_UNSEEN = 10;


    public abstract FavouritesDao getFavouritesDao();

    public abstract NotificationDao getNotificationDao();

}
