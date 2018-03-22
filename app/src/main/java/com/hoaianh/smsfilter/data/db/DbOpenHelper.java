
package com.hoaianh.smsfilter.data.db;

import android.content.Context;

import com.hoaianh.smsfilter.dagger.ApplicationContext;
import com.hoaianh.smsfilter.dagger.DatabaseInfo;
import com.hoaianh.smsfilter.data.db.model.dao.DaoMaster;
import com.hoaianh.smsfilter.utils.AppLogger;

import org.greenrobot.greendao.database.Database;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Nhat on 12/13/17.
 */


@Singleton
public class DbOpenHelper extends DaoMaster.OpenHelper {

    private static final String TAG = "DbOpenHelper";

    @Inject
    public DbOpenHelper(@ApplicationContext Context context, @DatabaseInfo String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        AppLogger.d(TAG, "DB_OLD_VERSION : " + oldVersion + ", DB_NEW_VERSION : " + newVersion);
        switch (oldVersion) {
            case 1:
            case 2:
                //db.execSQL("ALTER TABLE " + UserDao.TABLENAME + " ADD COLUMN "
                // + UserDao.Properties.Name.columnName + " TEXT DEFAULT 'DEFAULT_VAL'");
        }
    }
}
