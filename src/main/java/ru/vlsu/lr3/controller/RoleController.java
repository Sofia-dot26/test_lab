package ru.vlsu.lr3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vlsu.lr3.model.Role;
import ru.vlsu.lr3.service.RoleService;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public String listRoles(Model model) {
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        return "role";
    }

    @GetMapping("/add")
    public String addRoleForm(Model model) {
        model.addAttribute("role", new Role());
        return "roleForm";
    }

    @PostMapping("/add")
    public String addRole(@Valid @ModelAttribute Role role, BindingResult bindingResult) throws SQLException {
        if (bindingResult.hasErrors()) {
            return "roleForm";
        }
        roleService.addRole(role);
        return "redirect:/roles";
    }

    @GetMapping("/update/{id}")
    public String updateRoleForm(@PathVariable int id, Model model) {
        model.addAttribute("role", roleService.getRole((long) id));
        return "roleUpdate";
    }

    @PostMapping("/update/{id}")
    public String updateRole(@Valid @ModelAttribute Role role, BindingResult bindingResult, @PathVariable int id) throws SQLException {
        if (bindingResult.hasErrors()) {
            return "roleForm";
        }
        role.setId((long) id);
        roleService.updateRole(role);
        return "redirect:/roles";
    }

    @GetMapping("/delete/{id}")
    public String deleteRole(@PathVariable int id) {
        roleService.deleteRole((long) id);
        return "redirect:/roles";
    }
}
