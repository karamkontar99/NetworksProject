package main.services;

import main.files.FileManager;
import main.messages.FileDownloadRequest;
import main.messages.FileDownloadResponse;

import java.io.*;

public class FileDownloadService implements Service<FileDownloadRequest, FileDownloadResponse> {

    private final FileManager fileManager;

    public FileDownloadService(FileManager fileManager)
    {
        this.fileManager = fileManager;
    }

    @Override
    public FileDownloadResponse execute(FileDownloadRequest message) {
        // save the file on the local storage
        FileDownloadResponse response = new FileDownloadResponse();

        String fileName = message.fileName;
        if (!this.fileManager.hasFile(fileName)) {
            response.status = 0;
            return response;
        }

        response.status = 1;
        response.fileName = fileName;
        File file = null;
        InputStream is = null;
        try {
            file = this.fileManager.getFile(fileName);
            response.fileSize = (int) file.length();
            response.data = new byte[response.fileSize];
            int offset = 0;
            int numRead = 0;

            is = new FileInputStream(file);
            try {
                while (offset < response.fileSize
                        && (numRead=is.read(response.data, offset, response.data.length - offset)) >= 0) {
                    offset += numRead;
                }
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return response;
    }
}
