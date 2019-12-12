package edu.networks.project.services;

import android.util.Log;

import edu.networks.project.MySocket;
import edu.networks.project.messages.FileDownloadRequest;
import edu.networks.project.messages.FileDownloadResponse;

public class FileDownloadService {

    public static FileDownloadResponse execute(FileDownloadRequest request, MySocket.OnProgressUpdateListener listener) {
        FileDownloadResponse response = new FileDownloadResponse();
        MySocket socket;
        try {
            socket = new MySocket(listener);
            socket.sendMessage(request);
            response = (FileDownloadResponse) socket.readMessage();
            Log.e("SOCKETS", "status " + response.status);
            socket.close();
        } catch (Exception e) {
            response.status = 0;
            e.printStackTrace();
        }
        return response;
    }
}
