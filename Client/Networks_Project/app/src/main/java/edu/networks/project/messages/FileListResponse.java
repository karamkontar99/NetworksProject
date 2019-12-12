package edu.networks.project.messages;

public class FileListResponse implements MessageInterface {
    public int status;

    @Override
    public EMsg getEMsg() {
        return null;
    }

    @Override
    public byte[] serializeToByteArray() {
        return new byte[0];
    }

    @Override
    public void parseFromByteArray(byte[] array) throws Exception {

    }
}
