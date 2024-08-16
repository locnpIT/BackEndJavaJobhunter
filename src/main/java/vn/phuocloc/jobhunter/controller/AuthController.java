package vn.phuocloc.jobhunter.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.ipc.http.HttpSender.Response;
import jakarta.validation.Valid;
import vn.phuocloc.jobhunter.domain.User;
import vn.phuocloc.jobhunter.domain.dto.LoginDTO;
import vn.phuocloc.jobhunter.domain.dto.RestLoginDTO;
import vn.phuocloc.jobhunter.domain.dto.RestLoginDTO.UserLogin;
import vn.phuocloc.jobhunter.service.UserService;
import vn.phuocloc.jobhunter.util.SecurityUtil;
import vn.phuocloc.jobhunter.util.annotation.ApiMessage;
import vn.phuocloc.jobhunter.util.error.IdInvalidException;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securityUtil;
    private final UserService userService;

    @Value("${phuocloc.jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenExpiration;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder,
            SecurityUtil securityUtil, UserService userService) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
        this.userService = userService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<RestLoginDTO> login(@Valid @RequestBody LoginDTO loginDto) {
        // Nạp input gồm username/password vào Security
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword());

        // xác thực người dùng => cần viết hàm loadUserByUsername
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // create a token

        // set thông tin người dùng đăng nhập vào context (có thể sử dụng sau này)
        SecurityContextHolder.getContext().setAuthentication(authentication);

        RestLoginDTO res = new RestLoginDTO();
        User currentUserDB = this.userService.handleGetUserByUsername(loginDto.getUsername());
        if (currentUserDB != null) {
            RestLoginDTO.UserLogin userLogin = new RestLoginDTO.UserLogin(currentUserDB.getId(),
                    currentUserDB.getEmail(), currentUserDB.getName());
            res.setUser(userLogin);
        }
        String access_token = this.securityUtil.createAccessToken(authentication.getName(), res.getUser());
        res.setAccessToken(access_token);

        // create refreshToken
        String refresh_token = this.securityUtil.createRefreshToken(loginDto.getUsername(), res);

        // update user
        this.userService.updateUserToken(refresh_token, loginDto.getUsername());
        // set cookie
        ResponseCookie resCookie = ResponseCookie.from("refresh_token", refresh_token)
                .httpOnly(true)
                .path("/")
                .maxAge(refreshTokenExpiration) // thoi gian het han cua cookie
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, resCookie.toString())
                .body(res);
    }

    @GetMapping("/auth/account")
    @ApiMessage("fetch account")
    public ResponseEntity<RestLoginDTO.UserGetAccount> getAccount() {
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "";

        User currentUserDB = this.userService.handleGetUserByUsername(email);

        RestLoginDTO.UserLogin userLogin = new RestLoginDTO.UserLogin();
        RestLoginDTO.UserGetAccount userGetAccount = new RestLoginDTO.UserGetAccount();

        if (currentUserDB != null) {
            userLogin.setId(currentUserDB.getId());
            userLogin.setEmail(currentUserDB.getEmail());
            userLogin.setName(currentUserDB.getName());
            userGetAccount.setUser(userLogin);
        }
        return ResponseEntity.status(HttpStatus.OK).body(userGetAccount);

    }

    @GetMapping("/auth/refresh")
    @ApiMessage("Get User by refresh token")
    // lay token dua vao cookie
    public ResponseEntity<RestLoginDTO> getRefreshToken(
            @CookieValue(name = "refresh_token") String refresh_token) throws IdInvalidException {

        // check valid
        Jwt decodedToken = this.securityUtil
                .checkValidRefreshToken(refresh_token);
        String email = decodedToken.getSubject();
        // check user by token + email
        User currentUser = this.userService.getUserByRefreshTokenAndEmail(refresh_token, email);
        if (currentUser == null) {
            throw new IdInvalidException("Refresh token is not valid");
        }

        // tao token moi, set refresh token giong nhu la cookie

        RestLoginDTO res = new RestLoginDTO();
        User currentUserDB = this.userService.handleGetUserByUsername(email);
        if (currentUserDB != null) {
            RestLoginDTO.UserLogin userLogin = new RestLoginDTO.UserLogin(currentUserDB.getId(),
                    currentUserDB.getEmail(), currentUserDB.getName());
            res.setUser(userLogin);
        }
        String access_token = this.securityUtil.createAccessToken(email, res.getUser());
        res.setAccessToken(access_token);

        // create refreshToken
        String new_refresh_token = this.securityUtil.createRefreshToken(email, res);

        // update user
        this.userService.updateUserToken(new_refresh_token, email);
        // set cookie
        ResponseCookie resCookie = ResponseCookie.from("refresh_token", new_refresh_token)
                .httpOnly(true)
                .path("/")
                .maxAge(refreshTokenExpiration) // thoi gian het han cua cookie
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, resCookie.toString())
                .body(res);
    }

    @PostMapping("/auth/logout")
    @ApiMessage("Call api Logout")
    public ResponseEntity<Void> logout() throws IdInvalidException {
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "";

        if (email.equals("")) {
            throw new IdInvalidException("Access Token không hợp lệ");
        }

        // update refresh token = null
        this.userService.updateUserToken(null, email);

        // remove refresh token cookie
        ResponseCookie deleteSpringCookie = ResponseCookie
                .from("refresh_token", null)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, deleteSpringCookie.toString())
                .body(null);
    }

}
