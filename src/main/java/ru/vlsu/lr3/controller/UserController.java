package ru.vlsu.lr3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vlsu.lr3.beans.User;
import ru.vlsu.lr3.service.UserService;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user";
    }

    @GetMapping("/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        return "userForm";
    }

    @PostMapping("/add")
    public String addUser(@Valid @ModelAttribute User user, BindingResult bindingResult) throws SQLException {
        if (bindingResult.hasErrors()) {
            return "userForm";
        }
        userService.addUser(user);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @GetMapping("/update/{id}")
    public String updateUserForm(@PathVariable int id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "userUpdate";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@Valid @ModelAttribute User user, BindingResult bindingResult, @PathVariable int id) throws SQLException {
        if (bindingResult.hasErrors()) {
            return "userForm";
        }
        user.setId(id);
        userService.updateUser(user);
        return "redirect:/users";
    }
}
