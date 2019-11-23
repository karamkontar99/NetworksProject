package edu.networks.project.services;

import javax.inject.Inject;

import edu.networks.project.MySocket;
import edu.networks.project.messages.LoginRequest;
import edu.networks.project.messages.LoginResponse;

public class LoginService implements Service<LoginRequest, LoginResponse> {

    private final MySocket mySocket;

    @Inject
    public LoginService(MySocket mySocket) {
        this.mySocket = mySocket;
    }


    @Override
    public LoginResponse execute(LoginRequest request) {
        return null;
    }
}
