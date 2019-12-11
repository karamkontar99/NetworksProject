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
        response = new RegistrationResponse();
        if (!userRepository.uniqueUsername(request.username)) {

            //response.error = "username taken";
            response.status = 0;
        }
        else {
            User user = new User(request.name, request.address, request.email, request.username, request.password);
            userRepository.insert(user);
            response.status = 1;
            //response = mapper.map(user, RegistrationResponse.class);
        }
        return response;
    }
}
