package main.messages;

public class ExitResponse implements MessageInterface {

    @Override
    public EMsg getEMsg() {
        return EMsg.EExitResponse;
    }

    @Override
    public byte[] serializeToByteArray() {
        return new byte[0];
    }

    @Override
    public void parseFromByteArray(byte[] array) {

    }
}
