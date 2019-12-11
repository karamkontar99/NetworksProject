package edu.networks.project.dagger;

import dagger.Component;
import edu.networks.project.activities.LoginActivity;
import edu.networks.project.activities.RegistrationActivity;

@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    void inject(LoginActivity loginActivity);
    void inject(RegistrationActivity registrationActivity);
}
