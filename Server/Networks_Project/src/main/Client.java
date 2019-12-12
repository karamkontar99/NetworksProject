package main;

import main.messages.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Client {
    private final Socket clientSocket;
    private final DataInputStream is;
    private final DataOutputStream os;
    private final InetAddress ip;
    private final int port;

    public Client(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.is = new DataInputStream(clientSocket.getInputStream());
        this.os = new DataOutputStream(clientSocket.getOutputStream());
        ip = clientSocket.getInetAddress();
        port = clientSocket.getPort();
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
            is.close();
            os.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InetAddress getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
