package main.messages;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class RegistrationResponse implements MessageInterface {
	public int status;

	@Override
	public EMsg getEMsg() {
		return EMsg.ERegistrationResponse;
	}

	public void parseFromByteArray(byte[] bytes) throws Exception {
		byte[] statusB = Arrays.copyOfRange(bytes, 0, 4);
		int status = ByteBuffer.wrap(statusB).getInt();
		this.status = status;
	}

	public byte[] serializeToByteArray() {
		byte[] bytes = new byte[4];
		ByteBuffer.wrap(bytes, 0, 4).putInt(status);
		return bytes;
	}
}