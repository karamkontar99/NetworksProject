package main;

import main.messages.FileDownloadRequest;
import main.messages.FileUploadRequest;

public class test {

    public static void main(String[] args) throws Exception {
        FileDownloadRequest fd = new FileDownloadRequest();
        fd.fileName = "ali";

        byte[] a = fd.serializeToByteArray();


        FileDownloadRequest fd1 = new FileDownloadRequest();

        fd1.parseFromByteArray(a);

        System.out.println(fd1.fileName);


    }
}
