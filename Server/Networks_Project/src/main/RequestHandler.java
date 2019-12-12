package main;

import main.messages.LoginRequest;
import main.messages.MessageInterface;
import main.services.ClientServerUpload;
import main.services.LoginService;
import main.services.RegistrationService;
import services.ClientServerDownload;

import java.io.IOException;
import java.net.Socket;

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
            MessageInterface msg = client.readMessage();
            MessageInterface response = null;

            switch (msg.getEMsg()) {
                case ELoginRequest:
                    response = loginService.execute((LoginRequest) msg);
                    break;
                case ERegistrationRequest:
                    break;
                case EFileUploadRequest:
                    break;
                default:
                    break;
            }

            client.sendMessage(response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            client.close();
        }
    }

//    @Override
//    public void run() {
//        try {
//                Message request = client.readMessage();
//                Message response;
//
//                if (request instanceof LoginRequest)
//                    response = loginService.execute((LoginRequest) request);
//
//                else if (request instanceof RegistrationRequest)
//                    response = registrationService.execute((RegistrationRequest) request);
//
//                else if (request instanceof ExitRequest)
//                    response = new ExitResponse();
//
//                else {
//                    response = new ExitResponse();
//                    response.error = "unknown request";
//                }
//
//                response.date = new Date().toString();
//                client.sendMessage(response);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            client.close();
//        }
//    }
}
