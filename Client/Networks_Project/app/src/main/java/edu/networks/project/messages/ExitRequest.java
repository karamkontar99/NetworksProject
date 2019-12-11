package edu.networks.project.messages;

public class ExitRequest implements Message {

    public EMsg getEMsg()
    {
        return EMsg.EExistRequest;
    }

    @Override
    public byte[] serializeToByteArray() {
        return new byte[0];
    }

    @Override
    public void parseFromByteArray(byte[] array) {

    }
}
