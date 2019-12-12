package edu.networks.project.messages;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class FileListResponse implements MessageInterface {
    public int status;
    public int size;
    public List<String> names;
    public List<Integer> sizes;


    public void parseFromByteArray(byte[] bytes) throws Exception {
        /*
        first 4 bytes are the length (number of items) of the list
        then every set of bytes is the length of the name then the name then the filezise (4 bytes)

         */


        int index = 0;

        byte[] listsizeB = Arrays.copyOfRange(bytes, index, index + 4);
        int listsize = ByteBuffer.wrap(bytes, index, index + 4).getInt();
        index += 4;

        byte[] ItemLengthB = Arrays.copyOfRange(bytes, index, index + 4);
        int ItemLength = ByteBuffer.wrap(bytes, index, index + 4).getInt();
        index += 4;

        byte[] ItemNamelengthB = Arrays.copyOfRange(bytes, index, index + 4);
        int ItemNamelength = ByteBuffer.wrap(ItemNamelengthB).getInt();
        index += 4;

        byte[] ItemNameB = Arrays.copyOfRange(bytes, index, index + ItemNamelength);
        String fileName = new String(ItemNameB, StandardCharsets.US_ASCII);
        index += ItemNamelength;


        byte[] NextItemSizeB = Arrays.copyOfRange(bytes, index, index + 4);
        int NextItemSize = ByteBuffer.wrap(bytes, index, index + 4).getInt();
        index += 4;

//        this.listsize = listsize;
//        this.ItemLenght = ItemLength;
//        this.ItemName = fileName;
//        this.NextItemSize = NextItemSize;

    }


    @Override
    public EMsg getEMsg() {
        return null;
    }

    public byte[] serializeToByteArray() {
        int index = 0;
        byte[] bytes = null; //new byte[4 + 4 + 4 + 4 + listsize + ItemLenght + ItemName.length() + NextItemSize];
//
//        ByteBuffer.wrap(bytes, 0, 4).putInt(listsize);
//        index += 4;
//
//        ByteBuffer.wrap(bytes, index, 4).putInt(ItemLenght);
//        index += 4;
//
//        byte[] fileNameBytes = this.ItemName.getBytes(StandardCharsets.US_ASCII);
//        System.arraycopy(fileNameBytes, 0, bytes, index, ItemName.length());
//        index += ItemName.length();
//
//        ByteBuffer.wrap(bytes, index, 4).putInt(NextItemSize);
//        index += 4;

        return bytes;
    }
}