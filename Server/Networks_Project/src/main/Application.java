package main;

import eu.lestard.easydi.EasyDI;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class Application {
    private Logger logger = Logger.getLogger(Application.class.getName());

    private final EasyDI context;

    public Application(EasyDI context) {
        this.context = context;
    }

    private ServerSocket serverSocket;

    public void run(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                context.getInstance(RequestHandler.class).setClientSocket(clientSocket).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

}
