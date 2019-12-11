package main.services;

import main.files.FileManager;
import main.messages.*;

import java.io.IOException;

public class FileUploadService implements Service<FileUploadRequest, FileUploadResponse> {
    private final FileManager fileManager;

    public FileUploadService(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public  FileUploadResponse execute(FileUploadRequest request) throws IOException {
        FileUploadResponse response;
        response = new FileUploadResponse();

        if (fileManager.hasFile(request.fileName)) {
            response.status = 0;
        } else {
            response.status = 1;
            fileManager.createFile(request.fileName, request.data);
        }
        return response;
    }
}