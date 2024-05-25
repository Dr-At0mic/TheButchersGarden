package butchersgarden.main.auth.microservices.mailsender.controller;

import butchersgarden.main.auth.microservices.mailsender.component.AuthenticationComponent;
import butchersgarden.main.auth.microservices.mailsender.models.request.AccountActivationRequest;
import com.utils.AppCommonUtils.models.response.Response;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private AuthenticationComponent authenticationComponent;

    @PostMapping("/sendVerificationEmail")
    public ResponseEntity<Response> activateAccount(
            @Valid @RequestBody AccountActivationRequest accountActivationRequest) throws MessagingException {
        System.out.println(accountActivationRequest);
        Response response = authenticationComponent.sendMail(accountActivationRequest);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

}
