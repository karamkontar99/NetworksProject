package edu.networks.project.files;

import android.content.Context;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileManager {
    private final String DIR_NAME = "Networks_Project";
    private File dir;

    public FileManager(Context context) {
        dir = new File(context.getFilesDir(), DIR_NAME);
        assert dir.exists() || dir.mkdir();
    }

    public boolean hasFile(String name) {
        return getFile(name).exists();
    }

    public File getFile(String name) {
        return new File(dir, name);
    }

    public List<File> getAllFiles() {
        File[] files = dir.listFiles();
        if (files == null)
            files = new File[0];
        return Arrays.asList(files);
    }

    public void addFile(String name, File file) throws IOException {
        FileUtils.copyFile(file, getFile(name));
    }

    public void removeFile(String name) {
        File file = getFile(name);
        assert !file.exists() || file.delete();
    }

    public void renameFile(String oldName, String newName) throws IOException {
        FileUtils.moveFile(getFile(oldName), getFile(newName));
    }

    public List<byte[]> readFile(String name) throws IOException {
        FileInputStream stream = new FileInputStream(getFile(name));
        ArrayList<byte[]> list = new ArrayList<>();
        byte[] bytes = new byte[4096];
        while (stream.read(bytes) > 0) {
            list.add(bytes);
        }
        stream.close();
        return list;
    }

    public void writeFile(String name, int size, List<byte[]> list) throws IOException {
        FileOutputStream stream = new FileOutputStream(getFile(name));
        for (byte[] bytes : list) {
            stream.write(bytes, 0, Math.min(bytes.length, size));
            size -= bytes.length;
        }
        stream.close();
    }
}
