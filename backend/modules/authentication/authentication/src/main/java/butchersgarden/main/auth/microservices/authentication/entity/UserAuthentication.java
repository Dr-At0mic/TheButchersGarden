package butchersgarden.main.auth.microservices.authentication.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_authentication")
public class UserAuthentication implements Serializable {
    @Serial
    private static final long serialVersionUID = -5657450281784418379L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userAuthId;

    @Column
    private String refreshToken;

    @Column
    private LocalDateTime accountCreationDate;

    @Column(nullable = false)
    private String passwordHash;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    @JsonBackReference
    private User user;
}
