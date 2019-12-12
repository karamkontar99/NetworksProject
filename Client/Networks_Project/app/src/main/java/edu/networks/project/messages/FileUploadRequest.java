package edu.networks.project.messages;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class FileUploadRequest implements MessageInterface {
    public int fileSize;
    public String fileName;
    public byte[] data;

    @Override
    public EMsg getEMsg() {
        return EMsg.EFileUploadRequest;
    }

    public void parseFromByteArray(byte[] bytes) throws Exception {
        int index = 0;
        byte[] filseSizeB = Arrays.copyOfRange(bytes, index, index + 4);
        int fileSize = ByteBuffer.wrap(bytes, index, index + 4).getInt();

        index += 4;

        byte[] fileNamelengthB = Arrays.copyOfRange(bytes, index, index + 4);
        index += 4;
        int fileNamelength = ByteBuffer.wrap(fileNamelengthB).getInt();
        byte[] fileNameB = Arrays.copyOfRange(bytes, index, index + fileNamelength);
        index += fileNamelength;
        String fileName = new String(fileNameB, StandardCharsets.US_ASCII);

        byte[] datalengthB = Arrays.copyOfRange(bytes, index, index + 4);
        index += 4;
        int datalength = ByteBuffer.wrap(datalengthB).getInt();
        byte[] dataB = Arrays.copyOfRange(bytes, index, index + datalength);
        index += datalength;

        this.fileSize = fileSize;
        this.fileName = fileName;
        this.data = dataB;
    }

    public byte[] serializeToByteArray() {
        int index = 0;
        byte[] bytes = new byte[4 + 4 + 4 + fileSize + fileName.length() + data.length];
        // first 4 bytes should be usernameLength
        ByteBuffer.wrap(bytes, 0, 4).putInt(fileSize);
        index += 4;

        ByteBuffer.wrap(bytes, index, 4).putInt(fileName.length());
        index += 4;

        byte[] fileNameBytes = this.fileName.getBytes(StandardCharsets.US_ASCII);
        System.arraycopy(fileNameBytes, 0, bytes, index, fileName.length());
        index += fileName.length();

        ByteBuffer.wrap(bytes, index, 4).putInt(data.length);
        index += 4;

        byte[] dataBytes = this.data;
        System.arraycopy(dataBytes, 0, bytes, index, dataBytes.length);
        index += dataBytes.length;

        return bytes;
    }


}