package edu.networks.project.dagger;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.networks.project.MySocket;

@Module
public class ApplicationModule {

    @Singleton
    @Provides
    public MySocket provideMySocket() {
        try {
            return new MySocket("localhost", 5050);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
