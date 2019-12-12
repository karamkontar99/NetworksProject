package edu.networks.project.files;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FileManager {
    private final String DIR_NAME = "Networks_Project";
    private File dir;

    public FileManager(Context context) {
        dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), DIR_NAME);
        assert dir.exists() || dir.mkdir();
        Log.e("SOCKETS", "dir null? " + (dir == null));
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

    public byte[] readFile(String name) throws IOException {
        return FileUtils.readFileToByteArray(getFile(name));
    }

    public void writeFile(String name, byte[] data) throws IOException {
        FileUtils.writeByteArrayToFile(getFile(name), data);
    }
}
