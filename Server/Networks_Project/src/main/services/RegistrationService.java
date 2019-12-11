package main.services;

import main.messages.RegistrationRequest;
import main.messages.RegistrationResponse;
import main.models.User;
import main.repos.UserRepository;

public class RegistrationService implements Service<RegistrationRequest, RegistrationResponse> {
    private final UserRepository userRepository;

    public RegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public RegistrationResponse execute(RegistrationRequest request) {
        RegistrationResponse response;

        if (!userRepository.uniqueUsername(request.username)) {
            response = new RegistrationResponse();
            response.error = "username taken";
        }
        else {
            User user = new User(request.name, request.address, request.email, request.username, request.password);

            userRepository.insert(user);

            response = new RegistrationResponse();
            response.id = user.getId();
            response.name = user.getName();
            response.address = user.getAddress();
            response.email = user.getEmail();
            response.username = user.getUsername();
        }

        return response;
    }
}
