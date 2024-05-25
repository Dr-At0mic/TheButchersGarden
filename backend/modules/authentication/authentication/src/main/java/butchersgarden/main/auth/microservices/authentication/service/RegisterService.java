package butchersgarden.main.auth.microservices.authentication.service;

import butchersgarden.main.auth.microservices.authentication.entity.Role;
import butchersgarden.main.auth.microservices.authentication.entity.User;
import butchersgarden.main.auth.microservices.authentication.entity.UserAuthentication;
import butchersgarden.main.auth.microservices.authentication.repository.RoleRepository;
import butchersgarden.main.auth.microservices.authentication.repository.UserAuthRepository;
import butchersgarden.main.auth.microservices.authentication.repository.UserRepository;
import butchersgarden.main.auth.microservices.authentication.systemutils.BaseEncodeDecode;
import butchersgarden.main.auth.microservices.authentication.systemutils.JwtUtils;
import butchersgarden.main.auth.microservices.authentication.systemutils.SystemConstants;
import butchersgarden.main.auth.microservices.authentication.systemutils.UuidGenerator;
import com.utils.AppCommonUtils.models.request.AuthenticationRequest;
import com.utils.AppCommonUtils.models.response.Response;
import com.utils.AppCommonUtils.systemConstants.ErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class RegisterService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAuthRepository userAuthRepository;
    @Autowired
    private RoleRepository roleRepository;

    public Response createNewUser(AuthenticationRequest authenticationRequest) {
        try {
            User user = userRepository.save(User.builder()
                    .emailAddress(authenticationRequest.getEmailAddress())
                    .build());
            userAuthRepository.save(UserAuthentication.builder()
                    .user(user)
                    .passwordHash(BCrypt.hashpw(authenticationRequest.getPassword(), BCrypt.gensalt()))
                    .accountCreationDate(LocalDateTime.now())
                    .build());
            return Response.builder()
                    .status(true)
                    .data(BaseEncodeDecode.encode(JwtUtils.generateToken(user, SystemConstants.ACTIVATION_EXPIRATION_TIME)))
                    .message("Account Creation Success")
                    .ErrorCode(ErrorCode.SUCCESS)
                    .httpStatus(HttpStatus.CREATED)
                    .build();
        } catch (DataAccessException dae) {
            return Response.builder()
                    .message("Email Already Exist")
                    .ErrorCode(ErrorCode.EMAIL_DUPLICATION)
                    .httpStatus(HttpStatus.CONFLICT)
                    .build();
        } catch (Exception e) {
            return Response.builder()
                    .message("OOps something went wrong")
                    .ErrorCode(ErrorCode.TRANSACTIONAL_ERROR)
                    .httpStatus(HttpStatus.CONFLICT)
                    .build();
        }
    }

    public Response verifyEmail(String token) {
        if (JwtUtils.isTokenExpired(token))
            return Response.builder()
                    .message("token Expired")
                    .ErrorCode(ErrorCode.TOKEN_EXPIRED)
                    .httpStatus(HttpStatus.UNAUTHORIZED)
                    .build();
        Map<String, Object> map = JwtUtils.extractExtraClaims(token);
        Optional<User> optionalUser = userRepository.findById(((Number) map.get("User_Id")).longValue());
        if (optionalUser.isEmpty())
            return Response.builder()
                    .message("no User Found")
                    .ErrorCode(ErrorCode.USER_NOT_FOUND)
                    .httpStatus(HttpStatus.EXPECTATION_FAILED)
                    .build();
        if (optionalUser.get().isAccountStatus()) {
            return Response.builder()
                    .message("User Already Verified")
                    .ErrorCode(ErrorCode.FAILED)
                    .httpStatus(HttpStatus.ALREADY_REPORTED)
                    .build();
        }
        Optional<Role> customer_Role = roleRepository.findById(2L);
        List<Role> roleList = new ArrayList<>();
        if (customer_Role.isEmpty())
            return Response.builder()
                    .message("OOps something went wrong")
                    .ErrorCode(ErrorCode.TRANSACTIONAL_ERROR)
                    .httpStatus(HttpStatus.CONFLICT)
                    .build();
        roleList.add(customer_Role.get());
        optionalUser.get().setRoleList(roleList);
        optionalUser.get().setAccountStatus(true);
        optionalUser.get().setFullName("User-" + UuidGenerator.TempName());
        User user = userRepository.save(optionalUser.get());
        UserAuthentication userAuthentication = userAuthRepository.findByUser(optionalUser.get());
        userAuthentication.setRefreshToken(JwtUtils.generateToken(optionalUser.get(), SystemConstants.REFRESH_TOKEN_EXPIRATION_TIME));
        UserAuthentication userAuth = userAuthRepository.save(userAuthentication);

        return Response.builder()
                .status(true)
                .message("Account Activated")
                .ErrorCode(ErrorCode.SUCCESS)
                .httpStatus(HttpStatus.OK)
                .build();
    }
}
