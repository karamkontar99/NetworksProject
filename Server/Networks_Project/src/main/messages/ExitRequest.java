package main.messages;

public class ExitRequest implements MessageInterface {

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
