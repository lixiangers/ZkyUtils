package green.dao.htj.upgrade;

import android.database.sqlite.SQLiteDatabase;


public class MigrateV2ToV3 extends MigrationImpl {
    @Override
    public int applyMigration(SQLiteDatabase db,
                              int currentVersion) {
        super.prepareMigration(db, currentVersion);
        db.execSQL("ALTER TABLE DELIVERY_ORDER ADD COLUMN QUERY_BATCH TEXT");
        return getMigratedVersion();
    }

    @Override
    public int getTargetVersion() {
        return 2;
    }

    @Override
    public int getMigratedVersion() {
        return 3;
    }

    @Override
    public Migration getPreviousMigration() {
        return new MigrateV1ToV2();
    }
}
