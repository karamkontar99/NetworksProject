package edu.networks.project.dagger;

import javax.inject.Singleton;

import dagger.Component;
import edu.networks.project.activities.LoginActivity;
import edu.networks.project.activities.RegistrationActivity;

@Singleton
public interface ApplicationComponent {
    void inject(LoginActivity loginActivity);
    void inject(RegistrationActivity registrationActivity);
}
