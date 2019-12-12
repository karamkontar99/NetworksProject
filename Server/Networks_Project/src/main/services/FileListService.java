package main.services;

import main.files.FileManager;
import main.messages.FileListRequest;
import main.messages.FileListResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileListService {
    private final Logger logger = Logger.getLogger(getClass().getSimpleName());
    private final FileManager fileManager;

    public FileListService(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public FileListResponse execute(FileListRequest request) {
        FileListResponse response = new FileListResponse();

        logger.log(Level.INFO, "file list request");

        List<String> names = new ArrayList<>();
        List<Integer> sizes = new ArrayList<>();
        fileManager.getAllFiles().forEach(file -> {
            names.add(file.getName());
            sizes.add((int) file.length());
        });
        
        for (int i=0;i<names.size();i++)
            response.addFile(names.get(i),sizes.get(i));

        response.status = 1;

        return response;
    }


}
