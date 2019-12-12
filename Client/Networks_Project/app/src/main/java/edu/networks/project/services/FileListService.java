package edu.networks.project.services;

import android.util.Log;

import edu.networks.project.MySocket;
import edu.networks.project.messages.FileListRequest;
import edu.networks.project.messages.FileListResponse;

public class FileListService {

    public static FileListResponse execute(FileListRequest request) {
        FileListResponse response = new FileListResponse();
        MySocket socket;
        try {
            socket = new MySocket();
            socket.sendMessage(request);
            response = (FileListResponse) socket.readMessage();
            Log.e("SOCKETS", "status " + response.status);
            socket.close();
        } catch (Exception e) {
            response.status = 0;
            e.printStackTrace();
        }
        return response;
    }
}
