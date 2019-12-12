package main;

import main.messages.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    private final Socket clientSocket;
    private final InputStream is;
    private final OutputStream os;
    private final InetAddress ip;
    private final int port;

    public Client(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.is = clientSocket.getInputStream();
        this.os = clientSocket.getOutputStream();
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
                message = new ExitRequest();
                break;
            case 9:
                message = new ExitResponse();
                break;
            case 10:
                message = new FileListRequest();
                break;
            case 11:
                message = new FileListResponse();
                break;
            case 12:
                message = new FileDownloadRequest();
                break;
            case 13:
                message = new FileDownloadResponse();
                break;
            default:
                throw new Exception("Unknown EMsg");
        }
        logger.log(Level.INFO, "read " + Arrays.toString(data));

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

        logger.log(Level.INFO, "wrote " + Arrays.toString(payload));
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

//    public Message readMessage() throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
//        Class<Message> tClass = (Class<Message>) Class.forName(Message.class.getPackage().getName() + reader.readLine());
//        Message message = tClass.newInstance();
//        Field[] fields = tClass.getDeclaredFields();
//        while (true) {
//            final String line = reader.readLine();
//            if (line.isEmpty()) break;
//            Optional<Field> optionalField = Arrays.stream(fields).filter(f -> line.startsWith(f.getName())).findAny();
//            if (!optionalField.isPresent())
//                continue;
//            Field field = optionalField.get();
//            field.set(message, field.getType().cast(line.substring(field.getName().length())));
//        }
//        return message;
//    }
//
//    public void sendMessage(Message message) throws IOException, IllegalAccessException {
//        writer.write(message.getClass().getSimpleName() + "\r\n");
//        Field[] fields = message.getClass().getDeclaredFields();
//        for (Field field : fields)
//            writer.write(field.getName() + "=" + field.get(message).toString() + "\r\n");
//        writer.write("\r\n");
//        writer.flush();
//    }

    public void close() {
        try {
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
