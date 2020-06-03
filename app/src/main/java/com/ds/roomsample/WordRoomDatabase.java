package com.ds.roomsample;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Name.class}, version = 1, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {

    public abstract WordDAO wordDAO();

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
//                WordDAO dao = roomDatabaseInstance.wordDAO();
//                dao.deleteAll();
//
//                Name word = new Name("Hello");
//                dao.insert(word);
//                word = new Name("World");
//                dao.insert(word);
//            });
        }
    };

}
