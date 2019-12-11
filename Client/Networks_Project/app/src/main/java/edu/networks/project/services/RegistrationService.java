package edu.networks.project.services;

import javax.inject.Inject;

import edu.networks.project.MySocket;
import edu.networks.project.messages.RegistrationRequest;
import edu.networks.project.messages.RegistrationResponse;

public class RegistrationService implements Service<RegistrationRequest, RegistrationResponse> {
    private final MySocket mySocket;

    @Inject
    public RegistrationService(MySocket mySocket) {
        this.mySocket = mySocket;
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
