package com.ds.roomsample;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Name.class}, version = 3, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {

    public abstract NameDAO nameDAO();

    private static volatile WordRoomDatabase roomDatabaseInstance;
    private static final int Number_Of_Thread = 4;
    static final ExecutorService executorDatabaseWriteService = Executors.newFixedThreadPool(Number_Of_Thread);

    static WordRoomDatabase getDatabase (final Context context) {
        if (roomDatabaseInstance == null) {
            synchronized (WordRoomDatabase.class) {
                if (roomDatabaseInstance == null) {
                    roomDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class,
                            "word_database")
                            .addCallback(sRoomDatabaseCallback)
//                            .fallbackToDestructiveMigration()
                            .addMigrations(MIGRATION_1_2,MIGRATION_2_3)
                            .build();
                }
            }
        }
        return roomDatabaseInstance;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
//            executorDatabaseWriteService.execute(() -> {
//                // Populate the database in the background.
//                // If you want to start with more words, just add them.
//                NameDAO dao = roomDatabaseInstance.wordDAO();
//                dao.deleteAll();
//
//                Name word = new Name("Hello");
//                dao.insert(word);
//                word = new Name("World");
//                dao.insert(word);
//            });
        }
    };

//    https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
            Log.d("Test12345","Migrating DB 1 -> 2");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
            Log.d("Test12345","Migrating DB 2 -> 3");
            database.execSQL("ALTER TABLE 'name_table' "
                    + " ADD COLUMN 'id' INTEGER");
        }
    };


//    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            // Create the new table
//            database.execSQL(
//                    "CREATE TABLE users_new (userid TEXT, username TEXT, last_update INTEGER, PRIMARY KEY(userid))");// Copy the data
//            database.execSQL(
//                    "INSERT INTO users_new (userid, username, last_update) SELECT userid, username, last_update FROM users");// Remove the old table
//            database.execSQL("DROP TABLE users");// Change the table name to the correct one
//            database.execSQL("ALTER TABLE users_new RENAME TO users");
//        }
//    };


//    static final Migration MIGRATION_1_4 = new Migration(1, 4) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            // Create the new table
//            database.execSQL(
//                    "CREATE TABLE users_new (userid TEXT, username TEXT, last_update INTEGER, PRIMARY KEY(userid))");
//
//            // Copy the data
//            database.execSQL(
//                    "INSERT INTO users_new (userid, username, last_update) SELECT userid, username, last_update FROM users");// Remove the old table
//            database.execSQL("DROP TABLE users");// Change the table name to the correct one
//            database.execSQL("ALTER TABLE users_new RENAME TO users");
//        }
//    };
}
