package com.bestweby.enewz.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.bestweby.enewz.database.helpers.DaoHelper;
import com.bestweby.enewz.model.dbEntity.NotificationModel;

import java.util.List;

/**
 * Created by Md Sahidul Islam on 30-May-19.
 */
@Dao
public interface NotificationDao {

    @Insert
    void insert(NotificationModel notificationModel);

    @Query("SELECT * FROM " + DaoHelper.NOTIFICATION_TBL + " ORDER BY " + DaoHelper.COLUMN_AUTO_ID + " DESC")
    List<NotificationModel> getAll();

    @Query("SELECT * FROM " + DaoHelper.NOTIFICATION_TBL + " WHERE " + DaoHelper.COLUMN_NOTIFICATION_SEEN + " = 0")
    List<NotificationModel> getUnseenList();

    @Query("UPDATE " + DaoHelper.NOTIFICATION_TBL + " SET " + DaoHelper.COLUMN_NOTIFICATION_SEEN + "= 1 WHERE " + DaoHelper.COLUMN_AUTO_ID + "=:rowId")
    void updateSeenNotification(int rowId);

    @Query("DELETE FROM " + DaoHelper.NOTIFICATION_TBL + " WHERE " + DaoHelper.COLUMN_AUTO_ID + " = :rowId")
    void deleteNotification(int rowId);

    @Query("DELETE FROM " + DaoHelper.NOTIFICATION_TBL)
    void deleteAllNotification();

    @Delete
    void delete(NotificationModel notificationModel);
}
