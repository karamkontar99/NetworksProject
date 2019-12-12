package main.services;

import main.files.FileManager;
import main.messages.FileListRequest;

import java.util.ArrayList;
import java.util.List;

public class FileListService {
    private final FileManager fileManager;

    public FileListService(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public FileListRequest execute(FileListRequest request) {
        FileListRequest response = new FileListRequest();

        List<String> names = new ArrayList<>();
        List<Integer> sizes = new ArrayList<>();
        fileManager.getAllFiles().forEach(file -> {
            names.add(file.getName());
            sizes.add((int) file.length());
        });
        
        for (int i=0;i<names.size();i++)
            response.addFile(names.get(i),sizes.get(i));

        return response;
    }


}
