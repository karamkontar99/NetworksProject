package main.messages;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class RegistrationRequest extends Message {
    public String username;
    public String password;
    public String name;
    public String address;
    public String email;


    public void parseFromByteArray(byte[] bytes) throws Exception {
        int index = 0;
        byte[] usernameLengthB = Arrays.copyOfRange(bytes, index, index + 3);
        index += 4;
        int usernameLength = ByteBuffer.wrap(usernameLengthB).getInt();

        byte[] usernameB = Arrays.copyOfRange(bytes, index, index + usernameLength - 1);
        index += usernameLength;
        String username = new String(usernameB, StandardCharsets.US_ASCII);

        byte[] passwordLengthB = Arrays.copyOfRange(bytes, index, index + 3);
        index += 4;

        int passwordLength = ByteBuffer.wrap(passwordLengthB).getInt();
        byte[] passwordB = Arrays.copyOfRange(bytes, index, index + passwordLength - 1);
        index += passwordLength;
        String password = new String(passwordB, StandardCharsets.US_ASCII);


        this.username = username;
        this.password = password;
    }

    public byte[] serializeToByteArray() {
        int index = 0;
        byte[] bytes = new byte[4 + 4 + username.length() + password.length()];
        // first 4 bytes should be usernameLength
        ByteBuffer.wrap(bytes, 0, 4).putInt(username.length());
        index += 4;
        byte[] usernameBytes = this.username.getBytes(StandardCharsets.US_ASCII);
        System.arraycopy(usernameBytes, 0, bytes, index, username.length());
        index += username.length();

        ByteBuffer.wrap(bytes, index, 4).putInt(password.length());
        index += 4;
        byte[] passwordBytes = this.password.getBytes(StandardCharsets.US_ASCII);
        System.arraycopy(passwordBytes, 0, bytes, index, password.length());
        index += password.length();

        return bytes;
    }

}
