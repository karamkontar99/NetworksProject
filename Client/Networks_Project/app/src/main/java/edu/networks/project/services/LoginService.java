package edu.networks.project.services;

import android.util.Log;

import edu.networks.project.MySocket;
import edu.networks.project.messages.LoginRequest;
import edu.networks.project.messages.LoginResponse;

public class LoginService {

    public static LoginResponse execute(LoginRequest request) {
        LoginResponse response = new LoginResponse();
        MySocket socket;
        try {
            socket = new MySocket();
            socket.sendMessage(request);
            response = (LoginResponse) socket.readMessage();
            Log.e("SOCKETS", "status " + response.status);
            socket.close();
        } catch (Exception e) {
            response.status = 0;
            e.printStackTrace();
        }
        return response;
    }
}
