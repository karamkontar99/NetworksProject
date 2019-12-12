package main.services;

import main.messages.LoginRequest;
import main.messages.LoginResponse;
import main.models.User;
import main.repos.UserRepository;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginService implements Service<LoginRequest, LoginResponse> {
    private final UserRepository userRepository;
    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginResponse execute(LoginRequest request) {
        LoginResponse response;

        logger.log(Level.INFO, String.format("Login Request: username=%s password=%s\n", request.username, request.password));

        User user = userRepository.login(request.username, request.password);

        response = new LoginResponse();
        if (false || user == null) {
            response.status = 0;
        }
        else {
//            response = mapper.map(user, LoginResponse.class);
            response.status = 1;
        }

        return response;
    }

}
