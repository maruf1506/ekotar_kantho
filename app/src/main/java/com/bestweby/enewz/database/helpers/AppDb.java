package com.bestweby.enewz.database.helpers;


import android.content.Context;

import androidx.room.Room;


public class AppDb {

    private static DaoHelper daoHelper;

    public static DaoHelper getAppDb(Context context) {
        if (daoHelper == null) {
            daoHelper = Room.databaseBuilder(context, DaoHelper.class, DaoHelper.DATABASE_NAME)
                    //.addMigrations(MIGRATION_1_2)
                    .build();
        }
        return daoHelper;
    }

    public static void destroyInstance() {
        daoHelper = null;
    }
}
