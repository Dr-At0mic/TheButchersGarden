package butchersgarden.main.auth.microservices.mailsender.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountActivationRequest {
    @NotBlank(message = "email null in service - MailSender")
    @Email(message = "email format is wrong in service - mailSender")
    private String emailAddress;
    @NotBlank(message = "activation code's null in  service - MailSender")
    private String activationToken;
}
