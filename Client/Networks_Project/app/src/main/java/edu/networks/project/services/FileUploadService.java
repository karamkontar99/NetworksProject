package edu.networks.project.services;

import android.util.Log;

import edu.networks.project.MySocket;
import edu.networks.project.messages.FileUploadRequest;
import edu.networks.project.messages.FileUploadResponse;

public class FileUploadService {

    public static FileUploadResponse execute(FileUploadRequest request, MySocket.OnProgressUpdateListener listener) {
        FileUploadResponse response = new FileUploadResponse();
        MySocket socket;
        try {
            socket = new MySocket(listener);
            socket.sendMessage(request);
            response = (FileUploadResponse) socket.readMessage();
            Log.e("SOCKETS", "status " + response.status);
            socket.close();
        } catch (Exception e) {
            response.status = 0;
            e.printStackTrace();
        }
        return response;
    }
}
