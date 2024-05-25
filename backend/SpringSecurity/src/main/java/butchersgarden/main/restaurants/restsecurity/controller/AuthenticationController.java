package butchersgarden.main.restaurants.restsecurity.controller;

import butchersgarden.main.restaurants.restsecurity.config.AuthenticationConfig;
import com.utils.AppCommonUtils.models.request.AuthenticationRequest;
import com.utils.AppCommonUtils.models.response.Response;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationConfig authenticationConfig;

    @Autowired
    public AuthenticationController(AuthenticationConfig authenticationConfig) {
        this.authenticationConfig = authenticationConfig;
    }

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/register")
    public Response register(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        ResponseEntity<Response> response = null;
        try {
            response = restTemplate.postForEntity(authenticationConfig.getUrl() + "register", authenticationRequest, Response.class);
            return response.getBody();
        } catch (Exception e) {
            return Response.builder().data(e.getMessage()).build();
        }
    }

    @GetMapping("/accountActivation")
    public Response accountActivation(@Valid @RequestParam String token, HttpServletResponse httpServletResponse) {
        ResponseEntity<Response> response = null;
        try {
            response = restTemplate.getForEntity(authenticationConfig.getUrl() + "accountActivation?token=" + token, Response.class);
            return response.getBody();
        } catch (Exception e) {
            return Response.builder().data(e.getMessage()).build();
        }
    }

    @PostMapping("/login")
    public Response login(@Valid @RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse httpServletResponse) {
        ResponseEntity<Response> response = null;
        try {
            response = restTemplate.postForEntity(authenticationConfig.getUrl() + "login", authenticationRequest, Response.class);
            List<String> cookieHeaders = response.getHeaders().get(HttpHeaders.SET_COOKIE);
            if (cookieHeaders != null) {
                for (String cookieHeader : cookieHeaders) {
                    httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, cookieHeader);
                }
            }
            return response.getBody();
        } catch (Exception e) {
            return Response.builder().data(e.getMessage()).build();
        }
    }

}
