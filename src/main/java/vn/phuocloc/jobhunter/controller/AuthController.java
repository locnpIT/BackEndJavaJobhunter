package vn.phuocloc.jobhunter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.phuocloc.jobhunter.domain.User;
import vn.phuocloc.jobhunter.domain.dto.LoginDTO;
import vn.phuocloc.jobhunter.domain.dto.RestLoginDTO;
import vn.phuocloc.jobhunter.domain.dto.RestLoginDTO.UserLogin;
import vn.phuocloc.jobhunter.service.UserService;
import vn.phuocloc.jobhunter.util.SecurityUtil;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securityUtil;
    private final UserService userService;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder,
            SecurityUtil securityUtil, UserService userService) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<RestLoginDTO> login(@Valid @RequestBody LoginDTO loginDto) {
        // Nạp input gồm username/password vào Security
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword());

        // xác thực người dùng => cần viết hàm loadUserByUsername
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // create a token
        String access_token = this.securityUtil.createToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        RestLoginDTO res = new RestLoginDTO();

        User currentUserDB = this.userService.handleGetUserByUsername(loginDto.getUsername());
        if (currentUserDB != null) {
            RestLoginDTO.UserLogin userLogin = new RestLoginDTO.UserLogin(currentUserDB.getId(),
                    currentUserDB.getEmail(), currentUserDB.getName());
            res.setUser(userLogin);
        }
        res.setAccessToken(access_token);
        return ResponseEntity.ok().body(res);
    }
}
