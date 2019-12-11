package edu.networks.project.services;

import android.util.Log;

import edu.networks.project.MySocket;
import edu.networks.project.messages.LoginRequest;
import edu.networks.project.messages.LoginResponse;

public class LoginService {

    public static LoginResponse execute(LoginRequest request) {
        LoginResponse response = new LoginResponse();
        try {
            MySocket socket = new MySocket();
            Log.e("SOCKETS", "socket created");
            socket.sendMessage(request);
            Log.e("SOCKETS", "request sent");
            response = (LoginResponse) socket.readMessage();
            Log.e("SOCKETS", "response received");
            return response;
        } catch (Exception e) {
            response.status = 0;
            e.printStackTrace();
        }
        return response;
    }
}
