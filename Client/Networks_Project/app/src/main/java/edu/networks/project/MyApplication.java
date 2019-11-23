package edu.networks.project;

import android.app.Application;

import edu.networks.project.dagger.ApplicationComponent;
import edu.networks.project.dagger.DaggerApplicationComponent;

public class MyApplication extends Application {

    private ApplicationComponent applicationComponent;

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

}
