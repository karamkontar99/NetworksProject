package edu.networks.project.messages;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class FileDownloadRequest implements MessageInterface {

    public String fileName;

    public void parseFromByteArray(byte[] bytes) throws Exception {

        int index = 0;

        byte[] fileNamelengthB = Arrays.copyOfRange(bytes, index, index + 4);
        index += 4;
        int fileNamelength = ByteBuffer.wrap(fileNamelengthB).getInt();
        byte[] fileNameB = Arrays.copyOfRange(bytes, index, index + fileNamelength);
        index += fileNamelength;
        String fileName = new String(fileNameB, StandardCharsets.US_ASCII);

        this.fileName = fileName;
    }


    @Override
    public EMsg getEMsg() {
        return null;
    }

    public byte[] serializeToByteArray() {

        int index = 0;
        byte[] bytes = new byte[4 + fileName.length()];

        byte[] fileNameBytes = this.fileName.getBytes(StandardCharsets.US_ASCII);
        System.arraycopy(fileNameBytes, 0, bytes, index, fileName.length());
        index += fileName.length();


        return bytes;

    }


}
