package butchersgarden.main.auth.microservices.authentication.controller;

import butchersgarden.main.auth.microservices.authentication.component.AuthenticationComponent;
import com.utils.AppCommonUtils.models.request.AuthenticationRequest;
import com.utils.AppCommonUtils.models.response.Response;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationComponent authenticationComponent;

    @PostMapping("/register")
    public Response register(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        return authenticationComponent.newUser(authenticationRequest);
    }

    @GetMapping("/accountActivation")
    public Response accountActivation(@Valid @RequestParam String token) {
        return authenticationComponent.verifyEmail(token);
    }

    @PostMapping("/login")
    public Response login(@Valid @RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse httpServletResponse) {
        return authenticationComponent.verifyUser(authenticationRequest, httpServletResponse);
    }

}
