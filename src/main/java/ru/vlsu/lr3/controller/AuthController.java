package ru.vlsu.lr3.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import ru.vlsu.lr3.model.PasswordResetToken;
import ru.vlsu.lr3.model.Role;
import ru.vlsu.lr3.model.User;
import ru.vlsu.lr3.service.RoleService;
import ru.vlsu.lr3.service.SecurityServiceImpl;
import ru.vlsu.lr3.service.UserService;

import java.util.UUID;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityServiceImpl securityService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @Valid @ModelAttribute User user,
            BindingResult bindingResult,
            Model model,
            HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        // Проверка существования пользователя
        if (userService.getUserByUsername(user.getUsername()) != null) {
            model.addAttribute("errorRegister", "Пользователь с таким именем уже существует");
            return "register";
        }

        if (userService.getUserByEmail(user.getEmail()) != null) {
            model.addAttribute("errorRegister", "Пользователь с такой почтой уже существует");
            return "register";
        }

        // Установка роли USER
        Role userRole = roleService.getRoleByName("USER");
        if (userRole == null) {
            model.addAttribute("errorRegister", "Системная ошибка: роль USER не найдена");
            return "register";
        }

        user.setRoleId(userRole.getId());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.addUser(user);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model,
                        @RequestParam(required = false) String error,
                        @RequestParam(required = false) String blocked, HttpServletResponse response) {


        if (error != null) {
            model.addAttribute("error", "\n" + "Неправильное имя пользователя или пароль");
        }
        if (blocked != null) {
            model.addAttribute("error", "Аккаунт заблокирован. Пожалуйста, сбросьте пароль.");
        }
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(
            @ModelAttribute User user,
            HttpServletRequest request, HttpServletResponse response) {


        User baseUser = userService.getUserByUsername(user.getUsername());
        if (baseUser == null) {
            return "redirect:/login?error=true";
        }

        if (!passwordEncoder.matches(user.getPassword(), baseUser.getPassword())) {
            return "redirect:/login?error=true";
        }

        System.console().printf("\n\ngetPassword %s \n\n", user.getPassword());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword()


        );


//        authenticationManager.authenticate(authenticationToken);
        // Аутентификация пользователя

        if (!authenticationToken.isAuthenticated()) {
            return "redirect:/login?error=true";
        }
        System.console().printf("\n\ngetPassword %s \n\n", user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);


        return "redirect:/";

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        try {
            new SecurityContextLogoutHandler().logout(
                    request,
                    null,
                    SecurityContextHolder.getContext().getAuthentication()
            );
            return "redirect:/login?logout";
        } catch (Exception e) {
            return "redirect:/login?error";
        }
    }
}