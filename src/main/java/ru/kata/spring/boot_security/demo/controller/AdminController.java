package ru.kata.spring.boot_security.demo.controller;

import ru.kata.spring.boot_security.demo.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String adminPage(Principal principal, Model model) {
        List<User> userList = userService.findAll();
        model.addAttribute("userList", userList);
        model.addAttribute("currentUser", principal.getName());
        return "admin";
    }
    @PostMapping
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/addUser")
    public String newUserFormPage(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roleList", roleService.findAll());
        return "newUserForm";
    }

    @GetMapping("/updateUser/{id}")
    public String updateUserFormPage(@PathVariable("id") long id, Model model) {
        User user = userService.findById(id);
        user.setPassword(null);
        model.addAttribute("user", user);
        model.addAttribute("roleList", roleService.findAll());
        return "updateUserForm";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUserFormPage(@PathVariable("id") long id, Model model) {
        User user = userService.findById(id);
        user.setPassword(null);
        model.addAttribute("user", user);
        model.addAttribute("roleList", roleService.findAll());
        return "deleteUserForm";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") long userId) {
        userService.deleteUserById(userId);
        return "redirect:/admin";
    }
}