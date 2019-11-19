package main;

import main.services.ClientServerUpload;
import main.services.Login;
import main.services.Register;
import services.ClientServerDownload;

import java.io.IOException;
import java.net.Socket;

public class RequestHandler extends Thread {
    private final Login login;
    private final Register register;
    private final ClientServerUpload clientServerUpload;
    private final ClientServerDownload clientServerDownload;

    public RequestHandler(Login login, Register register, ClientServerUpload clientServerUpload, ClientServerDownload clientServerDownload) {
        this.login = login;
        this.register = register;
        this.clientServerUpload = clientServerUpload;
        this.clientServerDownload = clientServerDownload;
    }

    private Client client;

    public RequestHandler setClientSocket(Socket clientSocket) throws IOException {
        client = new Client(clientSocket);
        return this;
    }

    @Override
    public void run() {
        try {
            boolean running = true;
            while (running) {
                switch (client.receive()) {
                    case "LOGIN":
                        login.execute(client);
                        break;
                    case "REGISTER":
                        register.execute(client);
                        break;
                    case "CLIENT_SERVER_UPLOAD":
                        clientServerUpload.execute(client);
                        break;
                    case "CLIENT_SERVER_DOWNLOAD":
                        clientServerDownload.execute(client);
                        break;
                    case "EXIT":
                        client.send("EXIT");
                        running = false;
                        break;
                    default:
                        client.send("ERROR");
                        client.send("unrecongnized command");
                        running = false;
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }
}
