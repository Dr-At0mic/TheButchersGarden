package butchersgarden.main.auth.microservices.authentication.controller;

import butchersgarden.main.auth.microservices.authentication.entity.User;
import butchersgarden.main.auth.microservices.authentication.service.ResponderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/response")
public class ResponderController {

    @Autowired
    private ResponderService responderService;

    @GetMapping("/getUserEntity")
    public User getUser(@Valid @RequestParam String emailAddress) {
        return responderService.getUser(emailAddress);
    }
}
