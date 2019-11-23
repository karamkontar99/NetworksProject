package main.services;

import main.messages.LoginRequest;
import main.messages.LoginResponse;
import main.models.User;
import main.repos.UserRepository;
import org.modelmapper.ModelMapper;

public class LoginService implements Service<LoginRequest, LoginResponse> {
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public LoginService(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public LoginResponse execute(LoginRequest request) {
        LoginResponse response;

        User user = userRepository.login(request.username, request.password);

        if (user == null) {
            response = new LoginResponse();
            response.error = "invalid credentials";
        }
        else {
            response = mapper.map(user, LoginResponse.class);
        }

        return response;
    }

}
