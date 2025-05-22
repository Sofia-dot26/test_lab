package ru.vlsu.lr3.repository;

import ru.vlsu.lr3.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    User findByPassword(String password);
/*    List<User> findAllById(List<Long> ids);*/
    List<User> findByIdIn(List<Long> ids);
    List<User> findByUsernameContainingIgnoreCase(String username);


}
