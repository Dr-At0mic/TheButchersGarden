package butchersgarden.main.auth.microservices.authresponder.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Functions {
    private Long functionId;
    private String functionName;
}