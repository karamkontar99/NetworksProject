package main.messages;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class FileUploadResponse {
	public int status;

	public void parseFromByteArray(byte[] bytes) throws Exception {
		int index = 0;

		byte[] statusLengthB = Arrays.copyOfRange(bytes, index, index + 4);
		index += 4;
		int statusLength = ByteBuffer.wrap(statusLengthB).getInt();

		byte[] statusB = Arrays.copyOfRange(bytes, index, index + statusLength);
		index += statusLength;
		String status = new String(statusB, StandardCharsets.US_ASCII);

		this.status = status;
	}

	public byte[] serializeToByteArray() {
		int index = 0;
		byte[] bytes = new byte[4 + status.length()];

		ByteBuffer.wrap(bytes, 0, 4).putInt(status.length());
		index += 4;
		byte[] statusBytes = this.status.getBytes(StandardCharsets.US_ASCII);
		System.arraycopy(statusBytes, 0, bytes, index, status.length());
		index += status.length();

		return bytes;
	}

}
