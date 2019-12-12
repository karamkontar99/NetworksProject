package edu.networks.project;

import android.app.Application;

import java.net.ServerSocket;

import edu.networks.project.files.FileManager;

public class MyApplication extends Application {

    private ServerSocket serverSocket;
    private FileManager fileManager;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onTerminate() {
        super.onTerminate();

    }

}