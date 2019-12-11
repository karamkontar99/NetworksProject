package main;

import main.messages.Message;

import java.io.*;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Optional;

public class Client {
    private final Socket clientSocket;
    private final InputStream is;
    private final OutputStream os;
//    private final BufferedReader reader;
//    private final BufferedWriter writer;
    private final InetAddress ip;
    private final int port;

    public Client(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        InputStream inputStream = clientSocket.getInputStream();
        OutputStream outputStream = clientSocket.getOutputStream();
        this.is = inputStream;
        this.os = outputStream;
/*        reader = new BufferedReader(new InputStreamReader(inputStream));
        writer = new BufferedWriter(new OutputStreamWriter(outputStream));*/
        ip = clientSocket.getInetAddress();
        port = clientSocket.getPort();
    }

    public void read() throws Exception {
        // first we read the first 4 bytes to get the message size
        byte[] sizeB = this.blockingRead(4);
        int size = ByteBuffer.wrap(sizeB).getInt();
        // size represents the size of the message without the header
        byte[] codeB = this.blockingRead(4);
        int code = ByteBuffer.wrap(codeB).getInt();

        // Read the rest of the message
        byte[] message = this.blockingRead(size);

        switch (code) {
            case 0:
                // Process LoginRequest Message

                break;
            case 1:
                // Process loginResponse message
                break;
        }

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
