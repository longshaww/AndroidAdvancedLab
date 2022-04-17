package vn.edu.huflit.ttl_19dh110248.models;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database( entities = {vn.edu.huflit.ttl_19dh110248.models.Cart.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    public abstract vn.edu.huflit.ttl_19dh110248.models.CartDao cartDao();
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(1);

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class , "CartDB")
                    .build();
        }
        return INSTANCE;
    }
    public static void destroyInstance() {
        INSTANCE = null;
    }
}

