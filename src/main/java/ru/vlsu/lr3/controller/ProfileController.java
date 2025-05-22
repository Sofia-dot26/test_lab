package ru.vlsu.lr3.controller;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.vlsu.lr3.model.Notification;
import ru.vlsu.lr3.model.Project;
import ru.vlsu.lr3.model.User;
import ru.vlsu.lr3.service.NotificationService;
import ru.vlsu.lr3.service.ProjectService;
import ru.vlsu.lr3.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping
    @Transactional
    public String showProfile(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByUsername(username);

        // Получаем непрочитанные уведомления
        List<Notification> notifications = notificationService.getUnreadNotifications(user);
        long unreadCount = notificationService.getUnreadCount(user);


        model.addAttribute("user", user);
        model.addAttribute("notifications", notifications);
        model.addAttribute("unreadCount", unreadCount);
        return "profile/view";
    }

    @GetMapping("/edit")
    public String editProfileForm(Model model, HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        return "profile/edit";
    }

    @PostMapping("/edit")
    public String updateProfile(@Valid @ModelAttribute User user,
                                BindingResult bindingResult,
                                HttpServletRequest request,
                                RedirectAttributes redirectAttributes)throws SQLException {


        if (bindingResult.hasErrors()) {
            return "profile/edit";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = userService.getUserByUsername(auth.getName());

        if (currentUser.getId() != user.getId()) {
            redirectAttributes.addFlashAttribute("error", "Unauthorized attempt!");
            return "redirect:/profile";
        }

        try {
            userService.updateUser(user);
            return "redirect:/profile";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating profile: " + e.getMessage());
            return "redirect:/profile/edit";
        }
    }

    @PostMapping("/notifications/mark-as-read")
    public String markNotificationsAsRead(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(auth.getName());
        notificationService.markAllAsRead(user);
        return "redirect:/profile";
    }

    @GetMapping("/password")
    public String showChangePasswordForm(Model model, HttpServletRequest request) {
        return "profile/passwordForm";
    }

    @PostMapping("/password")
    public String ChangePasswordForm( @RequestParam String newPassword,
                                      @RequestParam String oldPassword,
                                      HttpServletRequest request,
                                      RedirectAttributes redirectAttributes) {


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(auth.getName());
        String pass = user.getPassword();
        if (!passwordEncoder.matches(oldPassword,pass)){
            redirectAttributes.addFlashAttribute("userPass", "Error updating profile: ");
            return "redirect:/profile/password";
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.save(user);
        System.console().printf("\n\nsave\n\n");

        return "redirect:/profile";
    }


}
