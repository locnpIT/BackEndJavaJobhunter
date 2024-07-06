package vn.phuocloc.jobhunter.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import vn.phuocloc.jobhunter.domain.User;
import vn.phuocloc.jobhunter.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // @GetMapping("/user/create")
    @PostMapping("/user")
    public User createNewUser(@RequestBody User postmanUser) {

        User user = new User();
        user.setEmail("npl2004qn@gmail.com");
        user.setName("Phuoc Loc");
        user.setPassword("123456");
        User userCreate = this.userService.handleCreateUser(postmanUser);
        return userCreate;
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        this.userService.deleteUserById(id);
        return "phuoclocne";
    }

    @GetMapping("/user/{id}")
    public User fetchUserById(@PathVariable("id") long id) {
        Optional<User> userGet = this.userService.fetchById(id);
        if (userGet.isPresent()) {
            return userGet.get();
        }
        return null;
    }

    @GetMapping("/user")
    public List<User> findAllUser() {
        return this.userService.findAllUsers();
    }

    @PutMapping("/user")
    public User updateUser(@RequestBody User userCreate) {
        return this.userService.handleUpdateUser(userCreate);
    }

}
