package vn.phuocloc.jobhunter.service;

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

}
