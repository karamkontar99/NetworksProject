package main.messages;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class FileListResponse implements  MessageInterface{
    public int listsize;
    public int ItemLenght;
    public String ItemName;
    public int NextItemSize;

    public void parseFromByteArray(byte[] bytes) throws Exception {
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

        this.listsize = listsize;
        this.ItemLenght = ItemLength;
        this.ItemName = fileName;
        this.NextItemSize = NextItemSize;

    }

    @Override
    public EMsg getEMsg() {
        return  EMsg.EFileListResponse;
    }

    public byte[] serializeToByteArray() {
        int index = 0;
        byte[] bytes = new byte[4 + 4 + 4 + 4 + listsize + ItemLenght + ItemName.length() + NextItemSize];

        ByteBuffer.wrap(bytes, 0, 4).putInt(listsize);
        index += 4;

        ByteBuffer.wrap(bytes, index, 4).putInt(ItemLenght);
        index += 4;

        byte[] fileNameBytes = this.ItemName.getBytes(StandardCharsets.US_ASCII);
        System.arraycopy(fileNameBytes, 0, bytes, index, ItemName.length());
        index += ItemName.length();

        ByteBuffer.wrap(bytes, index, 4).putInt(NextItemSize);
        index += 4;

        return bytes;
    }
}