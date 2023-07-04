package com.bestweby.enewz.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.bestweby.enewz.database.helpers.DaoHelper;
import com.bestweby.enewz.model.dbEntity.FavouritesModel;

import java.util.List;

@Dao
public interface FavouritesDao {

    @Insert
    void insert(FavouritesModel favoritesModel);

    @Query("SELECT * FROM " + DaoHelper.FAVOURITE_POST_TBL + " ORDER BY " + DaoHelper.COLUMN_AUTO_ID + " DESC")
    List<FavouritesModel> getAll();

    @Query("SELECT * FROM " + DaoHelper.FAVOURITE_POST_TBL + " WHERE " + DaoHelper.COLUMN_POST_ID + " = :postId LIMIT 1")
    FavouritesModel getFavoritePost(int postId);

    @Query("DELETE FROM " + DaoHelper.FAVOURITE_POST_TBL + " WHERE " + DaoHelper.COLUMN_POST_ID + " = :postId")
    void deleteFavoritePost(int postId);

    @Delete
    void delete(FavouritesModel favouritesModel);
}
