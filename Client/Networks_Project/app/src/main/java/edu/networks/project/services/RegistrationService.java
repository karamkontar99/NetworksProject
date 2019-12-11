package edu.networks.project.services;

import edu.networks.project.MySocket;
import edu.networks.project.messages.RegistrationRequest;
import edu.networks.project.messages.RegistrationResponse;

public class RegistrationService {

    public static RegistrationResponse execute(RegistrationRequest request) {
        RegistrationResponse response = new RegistrationResponse();
        try {
            MySocket socket = new MySocket();
            socket.sendMessage(request);
            response = (RegistrationResponse) socket.readMessage();
        } catch (Exception e) {
            response.status = 0;
            e.printStackTrace();
        }
        return response;
    }
}
