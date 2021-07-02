package com.mul_alexautoprogramm.clientlist.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Client.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public final static Object LOCK = new Object();
    public static final String DATABASE_NAME = "client_list_db";
    private static AppDataBase instanceDB;

    public static AppDataBase getInstanceDB(Context context){

        if (instanceDB == null) {

            synchronized (LOCK) {
                //create DataBase!!!
                instanceDB = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, AppDataBase.DATABASE_NAME).build();

            }

        }

        return instanceDB;
    }


    public abstract ClientDAO clientDAO();


}
