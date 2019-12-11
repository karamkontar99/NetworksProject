package edu.networks.project;

import android.app.Application;

import java.io.IOException;
import java.net.ServerSocket;

import edu.networks.project.dagger.ApplicationComponent;
import edu.networks.project.dagger.DaggerApplicationComponent;

public class MyApplication extends Application {

    private ApplicationComponent applicationComponent;
    private ServerSocket serverSocket;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.create();

    }

    @Override
    public void onTerminate() {
        super.onTerminate();

    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
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

}