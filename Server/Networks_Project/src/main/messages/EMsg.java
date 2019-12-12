package main.messages;

import java.util.HashMap;
import java.util.Map;

public enum EMsg {
    Invalid(0),
    ELoginRequest(1),
    ELoginResponse(2),
    ERegistrationRequest(3),
    ERegistrationResponse(4),
    EUserInfo(5),
    EFileUploadRequest(6),
    EFileUploadResponse(7),
    EExistRequest(8),
    EExitResponse(9),
    EFileListRequest(10),
    EFileListResponse(11);

    private int value;
    private static Map map = new HashMap<>();

    private EMsg(int value) {
        this.value = value;
    }

    static {
        for (EMsg emsg : EMsg.values()) {
            map.put(emsg.value, emsg);
        }
    }

    public static EMsg valueOf(int pageType) {
        return (EMsg) map.get(pageType);
    }

    public int getValue() {
        return value;
    }
}
