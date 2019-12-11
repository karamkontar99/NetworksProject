package main;

import main.messages.Message;

import java.io.*;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.Optional;

public class Client {
    private final Socket clientSocket;
    private final BufferedReader reader;
    private final BufferedWriter writer;
    private final InetAddress ip;
    private final int port;

    public Client(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        InputStream inputStream = clientSocket.getInputStream();
        OutputStream outputStream = clientSocket.getOutputStream();
        reader = new BufferedReader(new InputStreamReader(inputStream));
        writer = new BufferedWriter(new OutputStreamWriter(outputStream));
        ip = clientSocket.getInetAddress();
        port = clientSocket.getPort();
    }

    public Message readMessage() throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Class<Message> tClass = (Class<Message>) Class.forName(Message.class.getPackage().getName() + "." + reader.readLine());
        Message message = tClass.newInstance();
        Field[] fields = tClass.getFields();
        while (true) {
            final String line = reader.readLine();
            if (line.isEmpty()) break;
            Optional<Field> optionalField = Arrays.stream(fields).filter(f -> line.startsWith(f.getName())).findAny();
            if (!optionalField.isPresent())
                continue;
            Field field = optionalField.get();
            field.set(message, field.getType().cast(line.substring(field.getName().length())));
        }
        return message;
    }

    public void sendMessage(Message message) throws IOException, IllegalAccessException {
        writer.write(message.getClass().getSimpleName() + "\r\n");
        Field[] fields = message.getClass().getFields();
        for (Field field : fields) {
            if (field == null || field.get(message) == null)
                continue;
            writer.write(field.getName() + "=" + field.get(message).toString() + "\r\n");
        }
        writer.write("\r\n");
        writer.flush();
    }

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
