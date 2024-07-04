package vn.phuocloc.jobhunter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.phuocloc.jobhunter.domain.User;
import vn.phuocloc.jobhunter.service.UserService;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/create")
    public String createNewUser() {

        User user = new User();
        user.setEmail("npl2004qn@gmail.com");
        user.setName("Phuoc Loc");
        user.setPassword("123456");
        this.userService.handleCreateUser(user);
        return "create user";
    }

}
