package butchersgarden.main.auth.microservices.authentication.service;

import butchersgarden.main.auth.microservices.authentication.entity.User;
import butchersgarden.main.auth.microservices.authentication.entity.UserAuthentication;
import butchersgarden.main.auth.microservices.authentication.repository.UserAuthRepository;
import butchersgarden.main.auth.microservices.authentication.repository.UserRepository;
import butchersgarden.main.auth.microservices.authentication.systemutils.BaseEncodeDecode;
import butchersgarden.main.auth.microservices.authentication.systemutils.JwtUtils;
import butchersgarden.main.auth.microservices.authentication.systemutils.SystemConstants;
import com.utils.AppCommonUtils.models.response.Response;
import com.utils.AppCommonUtils.systemConstants.ErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class LoginService {
    @Autowired
    private UserAuthRepository userAuthRepository;

    @Autowired
    private UserRepository userRepository;

    public Response userLogin(String emailAddress, String password, HttpServletResponse httpServletResponse) {
        User user = userRepository.findByEmailAddress(emailAddress);
        if (null == user)
            return Response.builder()
                    .message("No User Exist")
                    .ErrorCode(ErrorCode.USER_NOT_FOUND)
                    .httpStatus(HttpStatus.UNAUTHORIZED)
                    .build();
        if (!user.isAccountStatus())
            return Response.builder()
                    .ErrorCode(ErrorCode.EMAIL_NOT_VERIFIED)
                    .message("Please Verify Your Email first Mail Dispatched")
                    .httpStatus(HttpStatus.NOT_ACCEPTABLE)
                    .data(BaseEncodeDecode.encode(JwtUtils.generateToken(user, SystemConstants.ACTIVATION_EXPIRATION_TIME)))
                    .build();
        UserAuthentication userAuthentication = userAuthRepository.findByUser(user);
        if (!BCrypt.checkpw(password, userAuthentication.getPasswordHash()))
            return Response.builder()
                    .ErrorCode(ErrorCode.EMAIL_NOT_VERIFIED)
                    .message("email or password is wrong")
                    .httpStatus(HttpStatus.UNAUTHORIZED)
                    .build();
        user.setLastLoginDate(LocalDateTime.now());
        user = userRepository.save(user);
        userAuthentication.setRefreshToken(JwtUtils.generateToken(user, SystemConstants.REFRESH_TOKEN_EXPIRATION_TIME));
        userAuthentication = userAuthRepository.save(userAuthentication);
        ResponseCookie accessTokenCookie = ResponseCookie.from("AccessToken", JwtUtils.generateToken(user, SystemConstants.ACCESS_TOKEN_EXPIRATION_TIME))
                .maxAge(Duration.ofDays(SystemConstants.ACCESS_TOKEN_EXPIRATION_TIME))
                .path("/")
                .httpOnly(false)
                .secure(false)
                .sameSite("None")
                .build();
        ResponseCookie refreshTokenCookie = ResponseCookie.from("RefreshToken", userAuthentication.getRefreshToken())
                .maxAge(Duration.ofDays(SystemConstants.REFRESH_TOKEN_EXPIRATION_TIME))
                .path("/")
                .httpOnly(false)
                .secure(false)
                .sameSite("None")
                .build();
        httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
        return Response.builder()
                .status(true)
                .message("Login Success")
                .httpStatus(HttpStatus.OK)
                .ErrorCode(ErrorCode.SUCCESS)
                .build();
    }
}
