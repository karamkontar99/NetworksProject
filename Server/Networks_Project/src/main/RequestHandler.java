package main;

import main.messages.*;
import main.services.*;

import java.io.IOException;
import java.net.Socket;

public class RequestHandler extends Thread {
    private final LoginService loginService;
    private final RegistrationService registrationService;
    private final FileUploadService fileUploadService;
    private final FileDownloadService fileDownloadService;
    private final FileListService fileListService;

    public RequestHandler(LoginService loginService, RegistrationService registrationService, FileUploadService fileUploadService, FileDownloadService fileDownloadService, FileListService fileListService) {
        this.loginService = loginService;
        this.registrationService = registrationService;
        this.fileUploadService = fileUploadService;
        this.fileDownloadService = fileDownloadService;
        this.fileListService = fileListService;
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
                    response = registrationService.execute((RegistrationRequest) msg);
                    break;
                case EFileUploadRequest:
                    response = fileUploadService.execute((FileUploadRequest) msg);
                    break;
                case EExistRequest:
                    break;
                case EFileListRequest:
                    response = fileListService.execute((FileListRequest) msg);
                    break;
                case EFileDownloadRequest:
                    response = fileDownloadService.execute((FileDownloadRequest) msg);
                    break;
            }
            client.sendMessage(response);

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
