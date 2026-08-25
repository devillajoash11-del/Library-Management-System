import java.util.HashMap;
import java.util.Map;

public class IDandPasswords {
    private final Map<String, String> loginInfo = new HashMap<>();

    public IDandPasswords() {
        loginInfo.put("user@gmail.com", "pizza123");
        loginInfo.put("Brometheus", "PASSWORD");
        loginInfo.put("BroCode", "abc123");
    }

    public Map<String, String> getLoginInfo() {
        return loginInfo;
    }
}
