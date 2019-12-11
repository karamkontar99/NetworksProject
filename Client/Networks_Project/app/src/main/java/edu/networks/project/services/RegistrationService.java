package edu.networks.project.services;

import java.io.IOException;

import javax.inject.Inject;

import edu.networks.project.MyApplication;
import edu.networks.project.MySocket;
import edu.networks.project.messages.RegistrationRequest;
import edu.networks.project.messages.RegistrationResponse;

public class RegistrationService implements Service<RegistrationRequest, RegistrationResponse> {

    private MySocket mySocket;

    @Inject
    public RegistrationService() {
        try {
            mySocket = MyApplication.getSocketToServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public RegistrationResponse execute(RegistrationRequest request) {
        RegistrationResponse response;
        try {
            mySocket.sendMessage(request);
            response = (RegistrationResponse) mySocket.readMessage();
        } catch (Exception e) {
            response = new RegistrationResponse();
            response.error = "failed to send or receive message";
        }
        return response;
    }
}
