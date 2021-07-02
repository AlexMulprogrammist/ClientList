package com.mul_alexautoprogramm.clientlist.database;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

//This is class translate MainStream in newThread.This class important Stream
public class AppExecutor {
    public static final Object LOCK = new Object();
    private static AppExecutor instanceAppExecutor;
    private final Executor discIO;
    private final Executor MainIO;
    private final Executor NetWorkIO;

    public AppExecutor(Executor discIO, Executor mainIO, Executor netWorkIO) {
        this.discIO = discIO;
        MainIO = mainIO;
        NetWorkIO = netWorkIO;
    }

    public static AppExecutor getInstance(){
        if(instanceAppExecutor == null){

            synchronized (LOCK){

                instanceAppExecutor = new AppExecutor(Executors.newSingleThreadExecutor(),new MainThreadHandler(), Executors.newFixedThreadPool(3));
            }

        }

        return instanceAppExecutor;
    }


    //this is class handler, he get fields in newThread(NetWorkIO) and use in MainIO. We can visible from dataBase in TextView
    private static class MainThreadHandler implements Executor{

        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }

    public Executor getDiscIO() {
        return discIO;
    }

    public Executor getMainIO() {
        return MainIO;
    }

    public Executor getNetWorkIO() {
        return NetWorkIO;
    }
}
