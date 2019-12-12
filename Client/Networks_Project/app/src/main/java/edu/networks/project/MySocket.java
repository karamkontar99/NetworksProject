package edu.networks.project;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;

import edu.networks.project.messages.ExitRequest;
import edu.networks.project.messages.ExitResponse;
import edu.networks.project.messages.FileUploadRequest;
import edu.networks.project.messages.FileUploadResponse;
import edu.networks.project.messages.LoginRequest;
import edu.networks.project.messages.LoginResponse;
import edu.networks.project.messages.MessageInterface;
import edu.networks.project.messages.RegistrationRequest;
import edu.networks.project.messages.RegistrationResponse;
import edu.networks.project.messages.UserInfo;

public class MySocket {
    private static String serverIp;

    private final Socket socket;
    private final DataInputStream is;
    private final DataOutputStream os;

    public MySocket() throws IOException {
        this.socket = new Socket(serverIp, 5050);
        this.is = new DataInputStream(socket.getInputStream());
        this.os = new DataOutputStream(socket.getOutputStream());
    }

    public MessageInterface readMessage() throws Exception {
        // first we read the first 4 bytes to get the message size
        byte[] sizeB = this.blockingRead(4);
        int size = ByteBuffer.wrap(sizeB).getInt();
        // size represents the size of the message without the header
        byte[] codeB = this.blockingRead(4);
        int code = ByteBuffer.wrap(codeB).getInt();

        // Read the rest of the message
        byte[] data = this.blockingRead(size);

        MessageInterface message = null;

        // TODO: Might be better to setup an EnumMap that
        // maps the enum to class name

        switch (code) {
            case 1:
                // Process LoginRequest Message
                message = new LoginRequest();
                break;
            case 2:
                // Process loginResponse message
                message = new LoginResponse();
                break;
            case 3:
                // Process RegistrationRequest message
                message = new RegistrationRequest();
                break;
            case 4:
                // Process RegistrationResponse message
                message = new RegistrationResponse();
                break;
            case 5:
                // Process UserInfo message
                message = new UserInfo();
                break;
            case 6:
                // Process FileUploadRequest
                message = new FileUploadRequest();
                break;
            case 7:
                // Process FileUploadResponse
                message = new FileUploadResponse();
                break;
            case 8:
                // Process ExitRequest
                message = new ExitRequest();
                break;
            case 9:
                // Process ExitResponse
                message = new ExitResponse();
                break;
            default:
                throw new Exception("Unknown EMsg");
        }
        message.parseFromByteArray(data);
        return message;
    }

    public void sendMessage(MessageInterface message) throws Exception {
        byte[] messageBytes = message.serializeToByteArray();
        byte[] payload = new byte[8 + messageBytes.length];

        ByteBuffer.wrap(payload, 0, 4).putInt(messageBytes.length);
        ByteBuffer.wrap(payload, 4, 4).putInt(message.getEMsg().getValue());
        System.arraycopy(messageBytes, 0, payload, 8, messageBytes.length);

        os.write(payload);
        os.flush();
    }

    public byte[] blockingRead(int size) throws Exception {
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

    
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setServerIp(String serverIp) {
        MySocket.serverIp = serverIp;
    }
}
