package main.messages;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class LoginRequest extends Message {
    public String username;
    public String password;

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
        byte[] bytes = new byte[4 + 4 + username.length() + password.length()];
        
    }

}
