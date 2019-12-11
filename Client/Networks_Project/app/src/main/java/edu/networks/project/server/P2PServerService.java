package edu.networks.project.server;

import java.net.ServerSocket;
import java.net.Socket;

import edu.networks.project.MySocket;

public class P2PServerService {

    public P2PServerService(ServerSocket serverSocket) {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                new P2PServerThread(new MySocket(clientSocket)).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
