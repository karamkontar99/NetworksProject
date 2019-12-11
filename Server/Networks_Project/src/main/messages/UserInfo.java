package main.messages;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class UserInfo implements MessageInterface {
	public String id;
	public String name;
	public String address;
	public String email;
	public String username;

	@Override
	public EMsg getEMsg() {
		return EMsg.EUserInfo;
	}

	@Override
	public byte[] serializeToByteArray() {
		return new byte[0];
	}

	@Override
	public void parseFromByteArray(byte[] array) throws Exception {

	}
}