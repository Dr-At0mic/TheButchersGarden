package butchersgarden.main.auth.microservices.authresponder.controller;

import butchersgarden.main.auth.microservices.authresponder.model.User_DTO;
import butchersgarden.main.auth.microservices.authresponder.service.ResponderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth-responder")
public class ResponderController {

    @Autowired
    private ResponderService responderService;

    @GetMapping("/GetEntity")
    public User_DTO test(@Valid @Email @RequestParam String emailAddress){
       return responderService.getUserRoles(emailAddress);
    }
}
