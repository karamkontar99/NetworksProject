package main.messages;

public class FileListRequest implements MessageInterface {
    @Override
    public EMsg getEMsg() {
        return EMsg.EFileListRequest;
    }

    public void parseFromByteArray(byte[] bytes) {

    }

    public byte[] serializeToByteArray() {
        return new byte[0];
    }
}
