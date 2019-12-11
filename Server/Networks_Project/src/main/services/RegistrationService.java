package main.services;

import main.messages.RegistrationRequest;
import main.messages.RegistrationResponse;
import main.models.User;
import main.repos.UserRepository;
import org.modelmapper.ModelMapper;

public class RegistrationService implements Service<RegistrationRequest, RegistrationResponse> {
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public RegistrationService(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
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
            User user = mapper.map(request, User.class);
            userRepository.insert(user);
            response.status = 1;
            //response = mapper.map(user, RegistrationResponse.class);
        }
        return response;
    }
}
