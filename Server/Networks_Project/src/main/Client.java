package main;

import java.io.*;
import java.net.Socket;

public class Client {
    private final Socket clientSocket;
    private final BufferedReader reader;
    private final BufferedWriter writer;

    public Client(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        InputStream inputStream = clientSocket.getInputStream();
        OutputStream outputStream = clientSocket.getOutputStream();
        reader = new BufferedReader(new InputStreamReader(inputStream));
        writer = new BufferedWriter(new OutputStreamWriter(outputStream));
    }

    public void send(String message) throws IOException {
        writer.write(message);
        writer.write("\r\n");
        writer.flush();
    }

    public String receive() throws IOException {
        String message = reader.readLine();
        if (message == null)
            message = "";
        return message;
    }

    public void close() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
