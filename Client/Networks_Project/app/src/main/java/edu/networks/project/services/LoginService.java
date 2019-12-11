package edu.networks.project.services;

import java.io.IOException;

import javax.inject.Inject;

import edu.networks.project.MyApplication;
import edu.networks.project.MySocket;
import edu.networks.project.messages.LoginRequest;
import edu.networks.project.messages.LoginResponse;

public class LoginService implements Service<LoginRequest, LoginResponse> {

    private MySocket mySocket;

    @Inject
    public LoginService() {
        try {
            mySocket = MyApplication.getSocketToServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public LoginResponse execute(LoginRequest request) {
        LoginResponse response;
        try {
            mySocket.sendMessage(request);
            response = (LoginResponse) mySocket.readMessage();
        } catch (Exception e) {
            response = new LoginResponse();
            response.error = "failed to send or receive message";
        }
        return response;
    }
}
