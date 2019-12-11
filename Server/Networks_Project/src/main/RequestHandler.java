package main;

import main.messages.*;
import main.services.FileUploadService;
import main.services.LoginService;
import main.services.RegistrationService;

import java.io.IOException;
import java.net.Socket;

public class RequestHandler extends Thread {
    private final LoginService loginService;
    private final RegistrationService registrationService;
    private final FileUploadService fileUploadService;
    private final services.FileDownloadService fileDownloadService;

    public RequestHandler(LoginService loginService, RegistrationService registrationService, FileUploadService fileUploadService, services.FileDownloadService fileDownloadService) {
        this.loginService = loginService;
        this.registrationService = registrationService;
        this.fileUploadService = fileUploadService;
        this.fileDownloadService = fileDownloadService;
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
            MessageInterface response;

            switch (msg.getEMsg()) {
                case ELoginRequest:
                    response = loginService.execute((LoginRequest) msg);
                    client.sendMessage(response);
                    break;
                case ERegistrationRequest:
                    response = FileUploadService.execute((FileUploadRequest) msg);
                    client.sendMessage(response);
                    break;
                case EFileUploadRequest:
                    break;
                case EExistRequest:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.close();
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
