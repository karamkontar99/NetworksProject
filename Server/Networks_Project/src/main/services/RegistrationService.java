package main.services;

import main.messages.RegistrationRequest;
import main.messages.RegistrationResponse;
import main.models.User;
import main.repos.UserRepository;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistrationService implements Service<RegistrationRequest, RegistrationResponse> {
    private final UserRepository userRepository;
    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    public RegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public RegistrationResponse execute(RegistrationRequest request) {
        RegistrationResponse response;

        logger.log(Level.INFO, String.format("Registration Request: name=%s address=%s email=%s username=%s password=%s\n", request.name,request.address, request.email, request.username, request.password));

        response = new RegistrationResponse();
        if (!userRepository.uniqueUsername(request.username)) {
            response.status = 0;
        }
        else {
            User user = new User(request.name, request.address, request.email, request.username, request.password);
            userRepository.insert(user);
            logger.log(Level.INFO, userRepository.findAll().toString());
            response.status = 1;
        }
        return response;
    }
}
