package main.files;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FileManager {
    private final String FILE_DIR = "Network_Project_File_Directory";
    private File dir;

    public FileManager() {
        dir = new File(FILE_DIR);
        assert dir.exists() || dir.mkdir();
    }

    public boolean hasFile(String name) {
        return Arrays.asList(Objects.requireNonNull(dir.list())).contains(name);
    }


    public void createFile(String name, byte[] content) throws IOException {
        File file = new File(getPath(name));
        assert file.createNewFile();

        try {
            OutputStream os = new FileOutputStream(file);
            os.write(content);
            os.close();
        } catch (Exception e) {
        }
    }


    public File getFile(String name) throws FileNotFoundException {
        File file = new File(getPath(name));
        if (!file.exists())
            throw new FileNotFoundException();
        return file;
    }

    private String getPath(String name) {
        return FILE_DIR + File.separator + name;
    }

    public List<File> getAllFiles() {
        List<File> files = new ArrayList<>();
        File[] dirFiles = dir.listFiles();
        if (dirFiles != null)
            files = Arrays.asList(dirFiles);
        return files;
    }
}