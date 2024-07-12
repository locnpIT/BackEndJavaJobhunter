package vn.phuocloc.jobhunter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.phuocloc.jobhunter.domain.User;
import vn.phuocloc.jobhunter.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User handleCreateUser(User user) {
        return this.userRepository.save(user);
    }

    public void deleteUserById(long id) {
        this.userRepository.deleteById(id);
    }

    public Optional<User> fetchById(long id) {
        return userRepository.findById(id);
    }

    public List<User> findAllUsers() {
        return this.userRepository.findAll();
    }

    public User handleGetUserByUsername(String username) {
        return this.userRepository.findByEmail(username);
    }

    public User handleUpdateUser(User userCreate) {
        User userUpdate = this.fetchById(userCreate.getId()).get();
        if (userUpdate != null) {
            userUpdate.setEmail(userCreate.getEmail());
            userUpdate.setPassword(userCreate.getPassword());
            userUpdate.setName(userCreate.getName());
            this.handleCreateUser(userUpdate);
        }
        return userUpdate;
    }

}
