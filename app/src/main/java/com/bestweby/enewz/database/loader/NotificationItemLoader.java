package com.bestweby.enewz.database.loader;

import android.content.Context;
import android.os.AsyncTask;

import com.bestweby.enewz.database.helpers.AppDb;
import com.bestweby.enewz.database.helpers.DaoHelper;
import com.bestweby.enewz.database.helpers.DbLoaderInterface;
import com.bestweby.enewz.model.dbEntity.NotificationModel;

import java.lang.ref.WeakReference;

/**
 * Created by Md Sahidul Islam on 30-May-19.
 */
public class NotificationItemLoader extends AsyncTask<Object, Void, Object> {

    private DbLoaderInterface dbLoaderInterface;
    private WeakReference<Context> weakContext;

    public NotificationItemLoader(Context context) {
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
            NotificationModel notificationModel = (NotificationModel) object[1];
            AppDb.getAppDb(context).getNotificationDao().insert(notificationModel);
        } else if (query == DaoHelper.FETCH_ALL) {
            return AppDb.getAppDb(context).getNotificationDao().getAll();
        } else if (query == DaoHelper.FETCH_UNSEEN) {
            return AppDb.getAppDb(context).getNotificationDao().getUnseenList();
        } else if (query == DaoHelper.UPDATE) {
            AppDb.getAppDb(context).getNotificationDao().updateSeenNotification((int) object[1]);
        }else if (query == DaoHelper.DELETE) {
            AppDb.getAppDb(context).getNotificationDao().deleteNotification((int) object[1]);
        } else if (query == DaoHelper.DELETE_ALL) {
            AppDb.getAppDb(context).getNotificationDao().deleteAllNotification();
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
