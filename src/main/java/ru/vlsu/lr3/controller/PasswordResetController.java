package ru.vlsu.lr3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vlsu.lr3.model.User;
import ru.vlsu.lr3.service.EmailService;
import ru.vlsu.lr3.service.UserService;
import ru.vlsu.lr3.model.PasswordResetToken;

import java.util.UUID;

@Controller
public class PasswordResetController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.url}")
    private String appUrl;

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam String email, Model model) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            model.addAttribute("error", "Пользователь с таким email не существует");
            return "forgot-password";
        }
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);
        try {
            emailService.sendPasswordResetEmail(user.getEmail(), token, appUrl);
            model.addAttribute("message", "Ссылка для сброса пароля была отправлена на ваш электронный адрес");
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при отправке электронного письма: " + e.getMessage());
        }
        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam String token, Model model) {
        PasswordResetToken resetToken = userService.getPasswordResetToken(token);
        if (resetToken == null || resetToken.isExpired()) {
            model.addAttribute("error", "Недействительный или просроченный токен");
            return "reset-password";
        }

        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam String token,
                                       @RequestParam String password,
                                       Model model) {
        PasswordResetToken resetToken = userService.getPasswordResetToken(token);
        if (resetToken == null || resetToken.isExpired()) {
            model.addAttribute("error", "Недействительный или просроченный токен");
            return "reset-password";
        }
        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(password));
        userService.save(user);

        userService.deletePasswordResetToken(resetToken.getId());
        model.addAttribute("message", "Пароль успешно изменен. Теперь вы можете войти в систему.");
        return "login";
    }
}
