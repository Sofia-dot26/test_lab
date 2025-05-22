package ru.vlsu.lr3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.vlsu.lr3.model.Role;
import ru.vlsu.lr3.model.User;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired // Добавлено для инициализации
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Получаем пользователя по имени
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь с именем пользователя не найден: " + username);
        }

        // Получаем роль пользователя
        Role role = roleService.getRole(user.getRoleId());
        if (role == null) {
            throw new UsernameNotFoundException("Роль для пользователя не найдена: " + username);
        }

        // Создаем UserDetails с ролью
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("ROLE_" + role.getName()) // Добавляем роль как authority
                .build();
    }
}