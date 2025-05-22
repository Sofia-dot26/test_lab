package ru.vlsu.lr3.repository;

import ru.vlsu.lr3.model.User;
import ru.vlsu.lr3.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
    PasswordResetToken findByUser(User user);
    void deleteByToken(String token);
    void deleteByUser(User user_id);
    void save(String token);
}