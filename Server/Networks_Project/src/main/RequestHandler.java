package main;

import main.messages.*;
import main.services.ClientServerUpload;
import main.services.LoginService;
import main.services.RegistrationService;
import services.ClientServerDownload;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public class RequestHandler extends Thread {
    private final LoginService loginService;
    private final RegistrationService registrationService;
    private final ClientServerUpload clientServerUpload;
    private final ClientServerDownload clientServerDownload;

    public RequestHandler(LoginService loginService, RegistrationService registrationService, ClientServerUpload clientServerUpload, ClientServerDownload clientServerDownload) {
        this.loginService = loginService;
        this.registrationService = registrationService;
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
            while (true) {
                Message request = client.readMessage();
                Message response;

                if (request instanceof LoginRequest)
                    response = loginService.execute((LoginRequest) request);

                else if (request instanceof RegistrationRequest)
                    response = registrationService.execute((RegistrationRequest) request);

                else if (request instanceof ExitRequest)
                    response = new ExitResponse();

                else {
                    response = new ExitResponse();
                    response.error = "unknown request";
                }

                response.date = new Date().toString();
                client.sendMessage(response);

                if (response instanceof ExitResponse)
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }
}
