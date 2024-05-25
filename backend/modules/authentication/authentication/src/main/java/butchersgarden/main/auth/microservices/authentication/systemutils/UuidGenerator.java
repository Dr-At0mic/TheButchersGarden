package butchersgarden.main.auth.microservices.authentication.systemutils;

import java.util.UUID;

public class UuidGenerator {
    public static String TempName() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
