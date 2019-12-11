package edu.networks.project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.net.Socket;
import java.util.Arrays;
import java.util.Optional;

import edu.networks.project.messages.Message;


public class MySocket {
    private final Socket clientSocket;
    private final BufferedReader reader;
    private final BufferedWriter writer;

    public MySocket(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        InputStream inputStream = clientSocket.getInputStream();
        OutputStream outputStream = clientSocket.getOutputStream();
        reader = new BufferedReader(new InputStreamReader(inputStream));
        writer = new BufferedWriter(new OutputStreamWriter(outputStream));
    }

    public MySocket() throws IOException {
        this("10.0.2.2");
    }

    public MySocket(String host) throws IOException {
        this.clientSocket = new Socket(host, 5050);
        InputStream inputStream = clientSocket.getInputStream();
        OutputStream outputStream = clientSocket.getOutputStream();
        reader = new BufferedReader(new InputStreamReader(inputStream));
        writer = new BufferedWriter(new OutputStreamWriter(outputStream));
    }

    public Message readMessage() throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Class<Message> tClass = (Class<Message>) Class.forName(Message.class.getPackage().getName() + reader.readLine());
        Message message = tClass.newInstance();
        Field[] fields = tClass.getDeclaredFields();
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
        Field[] fields = message.getClass().getDeclaredFields();
        for (Field field : fields)
            writer.write(field.getName() + "=" + field.get(message).toString() + "\r\n");
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
}
