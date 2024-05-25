package butchersgarden.main.auth.microservices.authentication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "functions")
public class Functions implements Serializable {
    @Serial
    private static final long serialVersionUID = -3958320461392413834L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long functionId;

    @Column
    private String functionName;
}