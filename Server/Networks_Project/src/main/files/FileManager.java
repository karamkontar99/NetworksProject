package main.files;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private final String FILE_DIR = "Network_Project_File_Directory";
    private File dir;

    @Inject
    public FileManager() {
        dir = new File(FILE_DIR);
        assert dir.exists() || dir.mkdir();
    }

    public boolean hasFile(String name) {
        return getFile(name).exists();
    }

    public void createFile(String name) throws IOException {
        assert getFile(name).createNewFile();
    }

    private File getFile(String name) {
        return new File(dir, name);
    }

    public List<byte[]> readFile(String name) throws IOException {
        FileInputStream stream = new FileInputStream(getFile(name));
        ArrayList<byte[]> list = new ArrayList<>();
        byte[] bytes = new byte[4096];
        while (stream.read(bytes) > 0) {
            list.add(bytes);
        }
        return list;
    }

    public void writeFile(String name, int size, List<byte[]> list) throws IOException {
        FileOutputStream stream = new FileOutputStream(getFile(name));
        for (byte[] bytes : list) {
            stream.write(bytes, 0, Math.min(bytes.length, size));
            size -= bytes.length;
        }
    }
}
