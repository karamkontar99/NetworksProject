package edu.networks.project;

import android.app.Application;

import java.io.IOException;
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

    public synchronized ServerSocket getServerSocket() {
        if (serverSocket == null)
            try {
                serverSocket = new ServerSocket(5050);
            } catch (IOException e) {
                e.printStackTrace();
            }
        return serverSocket;
    }

    public synchronized FileManager getFileManager() {
        if (fileManager == null)
            fileManager = new FileManager(this);
        return fileManager;
    }

}