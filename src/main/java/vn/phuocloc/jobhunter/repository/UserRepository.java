package vn.phuocloc.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.phuocloc.jobhunter.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User save(User user);

    public User findByEmail(String email);

}
