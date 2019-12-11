package main.services;

import main.messages.LoginRequest;
import main.messages.LoginResponse;
import main.models.User;
import main.repos.UserRepository;

public class LoginService implements Service<LoginRequest, LoginResponse> {
    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginResponse execute(LoginRequest request) {
        LoginResponse response;

        User user = userRepository.login(request.username, request.password);

        if (user == null) {
            response = new LoginResponse();
            response.error = "invalid credentials";
        }
        else {
            response = new LoginResponse();
            response.id = user.getId();
            response.name = user.getName();
            response.address = user.getAddress();
            response.email = user.getEmail();
            response.username = user.getUsername();
        }

        return response;
    }

}
