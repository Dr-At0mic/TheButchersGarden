package butchersgarden.main.restaurants.restsecurity.controller;

import butchersgarden.main.restaurants.restsecurity.config.JwtUtil;
import butchersgarden.main.restaurants.restsecurity.config.ReservationConfig;
import butchersgarden.main.restaurants.restsecurity.systemUtils.JwtExtraClaimsExtractor;
import com.restutils.CommonUtils.reservationmodles.CreateReservationRequest;
import com.restutils.CommonUtils.reservationmodles.EditReservationRequest;
import com.utils.AppCommonUtils.models.response.Response;
import com.utils.AppCommonUtils.systemConstants.SystemConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/reservation")
public class ReservationSecurityController {

    @Autowired
    private JwtUtil jwtUtil;
    private final ReservationConfig reservationConfig;

    @Autowired
    public ReservationSecurityController(ReservationConfig reservationConfig) {
        this.reservationConfig = reservationConfig;
    }

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/availableTables")
    public Response availableTables() {
        ResponseEntity<Response> response = null;
        try {
            response = restTemplate.getForEntity(reservationConfig.getUrl() + "availableTables", Response.class);
            return response.getBody();
        } catch (Exception e) {
            return Response.builder().data(e.getMessage()).build();
        }
    }

    @GetMapping("/reservedTables")
    public Response reservedTables(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        ResponseEntity<Response> response = null;
        try {
            String token = authorizationHeader.substring(7);
            Claims claims = jwtUtil.extractAllClaims(token);
            Long userId = JwtExtraClaimsExtractor.extractUserId(claims);
            response = restTemplate.getForEntity(reservationConfig.getUrl() + "reservedTables?userId=" + userId, Response.class);

            return response.getBody();
        } catch (Exception e) {
            return Response.builder().message("hello").data(e.getMessage()).build();
        }
    }

    @PostMapping("/reserveTable")
    public Response reserveTable(@RequestBody CreateReservationRequest createReservationRequest, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        Claims claims = jwtUtil.extractAllClaims(token);
        Long userId = JwtExtraClaimsExtractor.extractUserId(claims);
        createReservationRequest.setUserId(userId);
        ResponseEntity<Response> response = null;
        try {
            response = restTemplate.postForEntity(reservationConfig.getUrl() + "reserveTable", createReservationRequest, Response.class);
            return response.getBody();
        } catch (Exception e) {
            return Response.builder().data(e.getMessage()).build();
        }
    }

    @PutMapping("/editReservation")
    public Response editReservation(@Valid @RequestBody EditReservationRequest editReservationRequests, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        Claims claims = jwtUtil.extractAllClaims(token);
        Long userId = JwtExtraClaimsExtractor.extractUserId(claims);
        editReservationRequests.setUserId(userId);
        ResponseEntity<Response> response = null;
        try {
            response = restTemplate.postForEntity(reservationConfig.getUrl() + "editReservation", editReservationRequests, Response.class);
            return response.getBody();
        } catch (Exception e) {
            return Response.builder().data(e.getMessage()).build();
        }
    }


    @DeleteMapping("/cancelReservation")
    public Response cancelReservation(@Valid @RequestParam Long reservationId) {
        ResponseEntity<Response> response = null;
        try {
            response = restTemplate.postForEntity(reservationConfig.getUrl() + "cancelReservation", reservationId, Response.class);
            return response.getBody();
        } catch (Exception e) {
            return Response.builder().data(e.getMessage()).build();
        }
    }

}
