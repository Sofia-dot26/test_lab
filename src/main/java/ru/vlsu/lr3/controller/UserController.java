package ru.vlsu.lr3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vlsu.lr3.model.User;
import ru.vlsu.lr3.service.RoleService;
import ru.vlsu.lr3.service.UserService;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasRole('ADMIN')")

public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //используем
    @GetMapping
    public String listUsers(@RequestParam(required = false) String search,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            Model model, HttpServletRequest request) {


        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("allRoles", roleService.getAllRoles());
        List<User> users;
        if (search != null && !search.isEmpty()) {
            users = userService.searchUsersByUsername(search);
        } else {
            users = userService.getAllUsers();
        }


        model.addAttribute("users", users);
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "users/users";
   }
    //используем
    @GetMapping("/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        return "users/userForm";
    }
    //используем
    @PostMapping("/add")
    public String addUser(@Valid @ModelAttribute User user, BindingResult bindingResult) throws SQLException {
        if (bindingResult.hasErrors()) {
            return "users/userForm";
        }
        userService.addUser(user);
        return "redirect:/users";
    }
    //используем
   @GetMapping("/update/{id}")
    public String updateUserForm(@PathVariable int id, Model model) {

        model.addAttribute("user", userService.getUser((long) id));
        return "users/userUpdate";

    }
    //используем
    @PostMapping("/update/{id}")
    public String updateUser(@Valid @ModelAttribute User user,
                             @RequestParam(required = false) String newPassword,
                             BindingResult bindingResult,
                             @PathVariable int id,
                             HttpServletRequest request
    ) throws SQLException {



        if (bindingResult.hasErrors()) {
            return "users/userForm";
        }

        if (newPassword != null){
            user.setPassword(passwordEncoder.encode(newPassword));
        }

        userService.updateUser(user);
        return "redirect:/users";
    }
    //используем
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUser((long) id);
        return "redirect:/users";
    }



}
