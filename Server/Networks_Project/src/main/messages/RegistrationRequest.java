package main.messages;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.text.StringCharacterIterator;
import java.util.Arrays;

public class RegistrationRequest implements MessageInterface {
    public String username;
    public String password;
    public String name;
    public String address;
    public String email;

    @Override
    public EMsg getEMsg() {
        return EMsg.ERegistrationRequest;
    }

    public void parseFromByteArray(byte[] bytes) throws Exception {
        int index = 0;
        byte[] usernameLengthB = Arrays.copyOfRange(bytes, index, index + 4);
        index += 4;
        int usernameLength = ByteBuffer.wrap(usernameLengthB).getInt();

        byte[] usernameB = Arrays.copyOfRange(bytes, index, index + usernameLength);
        index += usernameLength;
        String username = new String(usernameB, StandardCharsets.US_ASCII);

        byte[] passwordLengthB = Arrays.copyOfRange(bytes, index, index + 4);
        index += 4;

        int passwordLength = ByteBuffer.wrap(passwordLengthB).getInt();
        byte[] passwordB = Arrays.copyOfRange(bytes, index, index + passwordLength);
        index += passwordLength;
        String password = new String(passwordB, StandardCharsets.US_ASCII);

        byte[] namelengthB = Arrays.copyOfRange(bytes, index, index + 4);
        index += 4;
        int namelength = ByteBuffer.wrap(namelengthB).getInt();
        byte[] nameB = Arrays.copyOfRange(bytes, index, index + namelength);
        index += namelength;
        String name = new String(nameB, StandardCharsets.US_ASCII);

        byte[] addresslengthB = Arrays.copyOfRange(bytes, index, index + 4);
        index += 4;
        int addresslength = ByteBuffer.wrap(addresslengthB).getInt();
        byte[] addressB = Arrays.copyOfRange(bytes, index, index + addresslength);
        index += addresslength;
        String address = new String(addressB, StandardCharsets.US_ASCII);

        byte[] emaillengthB = Arrays.copyOfRange(bytes, index, index + 4);
        index += 4;
        int emaillength = ByteBuffer.wrap(emaillengthB).getInt();
        byte[] emailB = Arrays.copyOfRange(bytes, index, index + emaillength);
        index += emaillength;
        String email = new String(emailB, StandardCharsets.US_ASCII);

        this.username = username;
        this.password = password;
        this.name = name;
        this.address = address;
        this.email = email;
    }

    public byte[] serializeToByteArray() {
        int index = 0;
        byte[] bytes = new byte[4 + 4 + 4 + 4 + 4 + username.length() + password.length() + name.length() + address.length() + email.length()];
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

        ByteBuffer.wrap(bytes, index, 4).putInt(name.length());
        index += 4;
        byte[] nameBytes = this.name.getBytes(StandardCharsets.US_ASCII);
        System.arraycopy(nameBytes, 0, bytes, index, name.length());
        index += name.length();


        ByteBuffer.wrap(bytes, index, 4).putInt(address.length());
        index += 4;
        byte[] addressBytes = this.address.getBytes(StandardCharsets.US_ASCII);
        System.arraycopy(addressBytes, 0, bytes, index, address.length());
        index += address.length();

        ByteBuffer.wrap(bytes, index, 4).putInt(email.length());
        index += 4;
        byte[] emailBytes = this.email.getBytes(StandardCharsets.US_ASCII);
        System.arraycopy(emailBytes, 0, bytes, index, email.length());
        index += email.length();

        return bytes;
    }

}