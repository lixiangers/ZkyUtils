package green.dao.htj.upgrade;

import android.database.sqlite.SQLiteDatabase;

import green.dao.htj.StoreInfoDao;


public class MigrateV1ToV2 extends MigrationImpl {

    @Override
    public int applyMigration(SQLiteDatabase db,
                              int currentVersion) {
        prepareMigration(db, currentVersion);
        StoreInfoDao.createTable(db, false);
        db.execSQL("ALTER TABLE DELIVERY_ORDER ADD COLUMN LAST_REMINDER_TIME TEXT");
        return getMigratedVersion();
    }

    @Override
    public int getTargetVersion() {
        return 1;
    }

    @Override
    public int getMigratedVersion() {
        return 2;
    }

    @Override
    public Migration getPreviousMigration() {
        return null;
    }
}