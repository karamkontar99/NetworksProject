package edu.networks.project.messages;

public interface MessageInterface {
    public EMsg getEMsg();
    public byte[] serializeToByteArray();
    public void parseFromByteArray(byte[] array) throws Exception;
}
