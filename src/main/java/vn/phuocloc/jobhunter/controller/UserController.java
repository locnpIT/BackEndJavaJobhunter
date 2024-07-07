package vn.phuocloc.jobhunter.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.ipc.http.HttpSender.Response;
import vn.phuocloc.jobhunter.domain.User;
import vn.phuocloc.jobhunter.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // @GetMapping("/users/create")

    @PostMapping("/users")
    public ResponseEntity<User> createNewUser(@RequestBody User postmanUser) {
        User userCreate = this.userService.handleCreateUser(postmanUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreate);
        // trong body chỉ được truyền đối tượng User bởi vì ResponseEntity<User>
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id) {
        this.userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body("userDelete");
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> fetchUserById(@PathVariable("id") long id) {
        Optional<User> userGet = this.userService.fetchById(id);
        if (userGet.isPresent()) {
            // return userGet.get();
            return ResponseEntity.status(HttpStatus.OK).body(userGet.get());
        }
        return null;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> findAllUser() {
        // return this.userService.findAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.findAllUsers());
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User userCreate) {
        // return this.userService.handleUpdateUser(userCreate);
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.handleUpdateUser(userCreate));

    }

}
