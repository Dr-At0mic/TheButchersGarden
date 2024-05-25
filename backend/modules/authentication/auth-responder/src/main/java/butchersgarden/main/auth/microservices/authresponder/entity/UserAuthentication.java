package butchersgarden.main.auth.microservices.authresponder.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthentication {
    private Long userAuthId;
    private String refreshToken;
    private LocalDateTime accountCreationDate;
    private String passwordHash;
    private User user;
}
