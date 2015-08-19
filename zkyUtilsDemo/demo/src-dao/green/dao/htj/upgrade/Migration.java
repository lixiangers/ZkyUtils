package green.dao.htj.upgrade;

import android.database.sqlite.SQLiteDatabase;

import org.jetbrains.annotations.Nullable;

public interface Migration {
    int applyMigration(SQLiteDatabase db, int currentVersion);

    @Nullable
    Migration getPreviousMigration();

    int getTargetVersion();

    int getMigratedVersion();
}