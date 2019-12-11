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

import edu.networks.project.messages.Message1;

public class MySocket1 {
    private final Socket clientSocket;
    private final BufferedReader reader;
    private final BufferedWriter writer;

    public MySocket1(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        InputStream inputStream = clientSocket.getInputStream();
        OutputStream outputStream = clientSocket.getOutputStream();
        reader = new BufferedReader(new InputStreamReader(inputStream));
        writer = new BufferedWriter(new OutputStreamWriter(outputStream));
    }

    public MySocket1(String host) throws IOException {
        this.clientSocket = new Socket(host, 5050);
        InputStream inputStream = clientSocket.getInputStream();
        OutputStream outputStream = clientSocket.getOutputStream();
        reader = new BufferedReader(new InputStreamReader(inputStream));
        writer = new BufferedWriter(new OutputStreamWriter(outputStream));
    }

    public Message1 readMessage() throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Class<Message1> tClass = (Class<Message1>) Class.forName(Message1.class.getPackage().getName() + reader.readLine());
        Message1 message1 = tClass.newInstance();
        Field[] fields = tClass.getDeclaredFields();
        while (true) {
            final String line = reader.readLine();
            if (line.isEmpty()) break;
            Optional<Field> optionalField = Arrays.stream(fields).filter(f -> line.startsWith(f.getName())).findAny();
            if (!optionalField.isPresent())
                continue;
            Field field = optionalField.get();
            field.set(message1, field.getType().cast(line.substring(field.getName().length())));
        }
        return message1;
    }

    public void sendMessage(Message1 message1) throws IOException, IllegalAccessException {
        writer.write(message1.getClass().getSimpleName() + "\r\n");
        Field[] fields = message1.getClass().getDeclaredFields();
        for (Field field : fields)
            writer.write(field.getName() + "=" + field.get(message1).toString() + "\r\n");
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
