package butchersgarden.main.auth.microservices.mailsender.component;

import butchersgarden.main.auth.microservices.mailsender.models.request.AccountActivationRequest;
import butchersgarden.main.auth.microservices.mailsender.service.EmailSender;
import com.utils.AppCommonUtils.models.response.Response;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationComponent {
    @Autowired
    private EmailSender mailSender;

    public Response sendMail(AccountActivationRequest accountActivationRequest) throws MessagingException {
        return mailSender.generateActivationMail(accountActivationRequest.getEmailAddress(), accountActivationRequest.getActivationToken());
    }
}
