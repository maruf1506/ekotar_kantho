package com.bestweby.enewz.database.loader;

import android.content.Context;
import android.os.AsyncTask;

import com.bestweby.enewz.database.helpers.AppDb;
import com.bestweby.enewz.database.helpers.DaoHelper;
import com.bestweby.enewz.database.helpers.DbLoaderInterface;
import com.bestweby.enewz.model.dbEntity.FavouritesModel;

import java.lang.ref.WeakReference;

public class FavouriteItemLoader extends AsyncTask<Object, Void, Object> {

    private DbLoaderInterface dbLoaderInterface;
    private WeakReference<Context> weakContext;

    public FavouriteItemLoader(Context context) {
        weakContext = new WeakReference<>(context);
    }

    public void setDbLoaderInterface(DbLoaderInterface dbLoaderInterface) {
        this.dbLoaderInterface = dbLoaderInterface;
    }

    @Override
    protected Object doInBackground(Object... object) {
        Context context = weakContext.get();
        int query = (int) object[0];

        if (query == DaoHelper.INSERT) {
            FavouritesModel favouritesModel = (FavouritesModel) object[1];
            AppDb.getAppDb(context).getFavouritesDao().insert(favouritesModel);
        } else if (query == DaoHelper.FETCH_ALL) {
            return AppDb.getAppDb(context).getFavouritesDao().getAll();
        } else if (query == DaoHelper.FETCH) {
            return AppDb.getAppDb(context).getFavouritesDao().getFavoritePost((int) object[1]);
        } else if (query == DaoHelper.DELETE) {
             AppDb.getAppDb(context).getFavouritesDao().deleteFavoritePost((int) object[1]);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        if (dbLoaderInterface != null) {
            dbLoaderInterface.onFinished(o);
        }
    }
}
