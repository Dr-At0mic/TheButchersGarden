package butchersgarden.main.auth.microservices.authentication.component;

import butchersgarden.main.auth.microservices.authentication.service.LoginService;
import butchersgarden.main.auth.microservices.authentication.service.RegisterService;
import butchersgarden.main.auth.microservices.authentication.systemutils.BaseEncodeDecode;
import com.utils.AppCommonUtils.models.request.AuthenticationRequest;
import com.utils.AppCommonUtils.models.response.Response;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationComponent {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private InvokeAnotherService invokeAnotherService;

    public Response newUser(AuthenticationRequest authenticationRequest) {
        Response response = registerService.createNewUser(authenticationRequest);
        if (!response.isStatus())
            return response;
        response.setMessage("Verification Email Send to you Account");
        invokeAnotherService.SendVerificationMail(authenticationRequest.getEmailAddress(), response.getData().toString());
        response.setData(null);
        return response;
    }

    public Response verifyEmail(String token) {
        return registerService.verifyEmail(BaseEncodeDecode.decode(token));
    }

    public Response verifyUser(AuthenticationRequest authenticationRequest, HttpServletResponse httpServletResponse) {
        Response response = loginService.userLogin(authenticationRequest.getEmailAddress(), authenticationRequest.getPassword(), httpServletResponse);
        if (!response.isStatus() && response.getErrorCode().equals("530")) {
            invokeAnotherService.SendVerificationMail(authenticationRequest.getEmailAddress(), (String) response.getData());
            response.setData(null);
            return response;
        }
        return response;
    }
}
