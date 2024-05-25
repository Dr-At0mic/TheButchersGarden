package butchersgarden.main.auth.microservices.authresponder.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long userId;
    private String fullName;
    private String emailAddress;
    private LocalDateTime lastLoginDate;
    private String profileUrl;
    private UserAuthentication userAuthentication;
    private List<Role> roleList;
    private boolean accountStatus;
}
