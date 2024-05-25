package butchersgarden.main.restaurants.restsecurity.systemUtils;

import io.jsonwebtoken.Claims;

import java.util.Map;
import java.util.stream.Collectors;

public class JwtExtraClaimsExtractor {

    public static Map<String, Object> extractExtraClaims(Claims claims) {
        return claims.entrySet().stream()
                .filter(entry -> !entry.getKey().equals("sub") && !entry.getKey().equals("exp") && !entry.getKey().equals("iat"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static Long extractUserId(Claims claims) {
        Map<String, Object> extraClaims = extractExtraClaims(claims);
        Object userId = extraClaims.get("User_Id");
        if (userId instanceof Integer) {
            return ((Integer) userId).longValue();
        } else if (userId instanceof Long) {
            return (Long) userId;
        } else {
            throw new IllegalArgumentException("User_Id claim is not an Integer or Long value");
        }
    }

    public static boolean extractAccountStatus(Claims claims) {
        Map<String, Object> extraClaims = extractExtraClaims(claims);
        return (boolean) extraClaims.get("Account_Status");
    }
}