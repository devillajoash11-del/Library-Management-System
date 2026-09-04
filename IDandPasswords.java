import java.util.HashMap;
import java.util.Map;

// Helper class para sa lumang map-based login implementation.
// Sa kasalukuyang system, anumang non-empty ID at password ang tinatanggap.
public class IDandPasswords {
    // Lalagyan ng login credentials kung gagamitin ng ibang bahagi ng app.
    private final Map<String, String> loginInfo = new HashMap<>();

    // Gumagawa ng walang hardcoded credentials para hindi nakatali ang login sa preset accounts.
    public IDandPasswords() {
        // Walang inilalagay na fixed credentials dito.
    }

    // Ibinabalik ang credential map para sa compatible na paggamit ng lumang login page.
    public Map<String, String> getLoginInfo() {
        return loginInfo;
    }
}
