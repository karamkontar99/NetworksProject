package main.messages;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class UserInfo extends Message {
	public String id;
	public String name;
	public String address;
	public String email;
	public String username;
}