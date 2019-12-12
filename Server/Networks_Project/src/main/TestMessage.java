package main;

import main.messages.FileListResponse;
import main.messages.LoginRequest;

import java.io.*;

public class TestMessage {

    public static void main(String[] args) throws Exception {
        LoginRequest rq = new LoginRequest();
        rq.username = "Hello there";
        rq.password ="World world 1";

        byte[] bytes = rq.serializeToByteArray();

        LoginRequest rq2 = new LoginRequest();
        rq2.parseFromByteArray(bytes);
        System.out.println("Username is: '" + rq2.username + "'");
        System.out.println("Password is: '" + rq2.password + "'");

        FileListResponse res = new FileListResponse();
        res.status = 1;
        res.addFile("File 1", 1024);
        bytes = res.serializeToByteArray();

        System.out.println(res.sizes.get(0));

        FileListResponse res2 = new FileListResponse();
        res2.parseFromByteArray(bytes);
        System.out.println(res2.names.toString());
        System.out.println(res2.sizes.toString());

        //write();
        //read();
    }

    public static void write()
    {
        System.out.println("Writing message to loginRequest.txt");
        LoginRequest rq = new LoginRequest();
        rq.username = "Hello there";
        rq.password ="World world 1";
        byte[] bytes = rq.serializeToByteArray();

        String fileName = "loginRequest.txt";

        BufferedOutputStream bs = null;
        try {
            FileOutputStream fs = new FileOutputStream(new File(fileName));
            bs = new BufferedOutputStream(fs);
            bs.write(bytes);
            bs.close();
            bs = null;

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (bs != null) try { bs.close(); } catch (Exception e) {}
    }

    public static void read()
    {
        System.out.println("Reading message from loginRequest.txt");

        String fileName = "loginRequest.txt";

        BufferedInputStream is = null;
        File file = new File(fileName);
        try {
            FileInputStream fs = new FileInputStream(file);
            is = new BufferedInputStream(fs);
            long length = file.length();
            byte[] bytes = blockingRead(is, (int) length);
            LoginRequest req = new LoginRequest();
            req.parseFromByteArray(bytes);
            System.out.println("Username is: '" + req.username + "'");
            System.out.println("Password is: '" + req.password + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static byte[] blockingRead(BufferedInputStream is, int size) throws Exception {
        byte[] data = new byte[size];
        int read = 0;
        int result;
        while (read < size) {
            result = is.read(data, read, size - read);
            if (result == -1) {
                throw new Exception("blockingRead error: reached EOF");
            }
            read += result;
        }
        return data;
    }
}
