package main.services;

import main.files.FileManager;
import main.messages.FileUploadRequest;
import main.messages.FileUploadResponse;

public class FileUploadService implements Service<FileUploadRequest, FileUploadResponse> {
    private final FileManager fileManager;

    public FileUploadService(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public  FileUploadResponse execute(FileUploadRequest request) {
        FileUploadResponse response;
        response = new FileUploadResponse();

        try {
            if (fileManager.hasFile(request.fileName)) {
                response.status = 0;
            } else {
                response.status = 1;
                fileManager.createFile(request.fileName, request.data);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.status = 0;
        }

        return response;
    }
}