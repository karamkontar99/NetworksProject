package main.files;

import org.graalvm.compiler.graph.spi.Canonicalizable;

import java.io.*;
import java.util.Arrays;
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
}
