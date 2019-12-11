package edu.networks.project;

import android.app.Application;

import java.io.IOException;
import java.net.ServerSocket;

import edu.networks.project.dagger.ApplicationComponent;
import edu.networks.project.dagger.DaggerApplicationComponent;

public class MyApplication extends Application {

    private ApplicationComponent applicationComponent;
    private ServerSocket serverSocket;

    private static String hostIp;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.create();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

    }

    public static String getHostIp() {
        return hostIp;
    }

    public static void setHostIp(String _hostIp) {
        hostIp = _hostIp;
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

    public static MySocket getSocketToServer() throws IOException {
        return new MySocket(hostIp);
    }
}