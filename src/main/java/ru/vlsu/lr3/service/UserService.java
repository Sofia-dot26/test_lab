package ru.vlsu.lr3.service;

import org.hibernate.Session;
import ru.vlsu.lr3.model.PasswordResetToken;
import ru.vlsu.lr3.model.Project;
import ru.vlsu.lr3.model.Role;
import ru.vlsu.lr3.model.User;
import ru.vlsu.lr3.repository.PasswordResetTokenRepository;
import ru.vlsu.lr3.repository.RoleRepository;
import ru.vlsu.lr3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final RoleRepository roleRepository;
    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordResetTokenRepository passwordResetTokenRepository) {
        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.roleRepository = roleRepository;
    }
    @Transactional
    public void addUser(User user) {
        userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void updateUser(User user) {
        userRepository.save(user);
    }


    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getUsersByIds(List<Long> ids) {
        return userRepository.findByIdIn(ids);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public User getUserByPassword(String password){ return userRepository.findByPassword(password);}
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    @Transactional  // Добавьте, если нет
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    @Transactional
    public void createPasswordResetTokenForUser(User user, String token) {

        // Удаляем старые токены для этого пользователя
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByUser(user);
        passwordResetTokenRepository.deleteById(passwordResetToken.getId());
        passwordResetTokenRepository.deleteByUser(user);
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(calculateExpiryDate());
        passwordResetTokenRepository.save(resetToken);
    }

    private Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 24); // Токен действителен 24 часа
        return cal.getTime();
    }

    public PasswordResetToken getPasswordResetToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    @Transactional
    public void deletePasswordResetToken(Long idToken) {
        passwordResetTokenRepository.deleteById(idToken);
    }
    public void save(User user){
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    @Transactional(readOnly = true)
    public User getUserByUsernameWithProjects(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            // Инициализируем коллекции
            user.getManagedProjects();
            user.getManagedProjects().size();
            user.getParticipatingProjects().size();
            user.getAssignedTasks().size();
        }
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> searchUsersByUsername(String username) {
        return userRepository.findByUsernameContainingIgnoreCase(username);
    }


}
