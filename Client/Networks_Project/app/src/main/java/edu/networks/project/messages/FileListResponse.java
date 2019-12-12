package edu.networks.project.messages;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class FileListResponse implements  MessageInterface{
    public int status;
    public List<String> names;
    public List<Integer> sizes;

    public FileListResponse()
    {
        this.names = new LinkedList<String>();
        this.sizes = new LinkedList<Integer>();
    }

    public void addFile(String name, int size)
    {
        this.names.add(name);
        this.sizes.add(size);
    }

    public void parseFromByteArray(byte[] bytes) throws Exception {
        int index = 0;

        this.status = ByteBuffer.wrap(bytes, index, 4).getInt();
        index += 4;

        if (this.status != 1) {
            // if the response is not successful, there wont be a list of files
            return;
        }

        int count = ByteBuffer.wrap(bytes, index, 4).getInt();
        index += 4;

        for (int i = 0; i < count; i++) {
            // first read the first 4 bytes to get the file size
            // then read the next 4 bytes to get the string length
            // then read the string
            int size = ByteBuffer.wrap(bytes, index, 4).getInt();
            index += 4;
            int fileNameLength = ByteBuffer.wrap(bytes, index, 4).getInt();
            index += 4;
            String fileName = new String(bytes, index, fileNameLength, StandardCharsets.US_ASCII);
            index += fileNameLength;
            this.addFile(fileName, size);
        }
    }

    @Override
    public EMsg getEMsg() {
        return  EMsg.EFileListResponse;
    }

    public byte[] serializeToByteArray() {
        int index = 0;
        byte[] bytes = null;

        if (this.status != 1) {
            bytes = new byte[4];
            ByteBuffer.wrap(bytes).putInt(this.status);
            return bytes;
        }

        int neededBytes = 4 + 4;

        // calculate needed bytes to serialize lit
        for (int i = 0; i < this.names.size(); i++) {
            neededBytes += 4 + 4 + this.names.get(i).length();
        }

        bytes = new byte[4 + 4 + neededBytes];

        ByteBuffer.wrap(bytes, index, 4).putInt(this.status);
        index += 4;

        // put element count
        ByteBuffer.wrap(bytes, index, 4).putInt(this.names.size());
        index += 4;

        for (int i = 0; i < this.names.size(); i++) {
            ByteBuffer.wrap(bytes, index, 4).putInt(this.sizes.get(i));
            index += 4;
            ByteBuffer.wrap(bytes, index, 4).putInt(this.names.get(i).length());
            index += 4;
            System.arraycopy(this.names.get(i).getBytes(StandardCharsets.US_ASCII), 0, bytes, index, this.names.get(i).length());
            index += this.names.get(i).length();
        }

        return bytes;
    }
}