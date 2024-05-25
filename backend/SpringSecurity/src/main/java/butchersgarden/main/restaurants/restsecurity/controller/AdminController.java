package butchersgarden.main.restaurants.restsecurity.controller;

import com.utils.AppCommonUtils.models.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private RestTemplate restTemplate;

    @DeleteMapping("/deleteAllTable")
    public ResponseEntity<Response> deleteAllTable() {
        return new ResponseEntity<>(Response.builder()
                .status(true)
                .message("success")
                .httpStatus(HttpStatus.OK)
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/deleteAllBite")
    public ResponseEntity<Response> deleteAllBite() {
        return new ResponseEntity<>(Response.builder()
                .status(true)
                .message("success")
                .httpStatus(HttpStatus.OK)
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/deleteAllReservation")
    public ResponseEntity<Response> cancelReservation() {
        return new ResponseEntity<>(Response.builder()
                .status(true)
                .message("success")
                .httpStatus(HttpStatus.OK)
                .build(), HttpStatus.OK);
    }

}
