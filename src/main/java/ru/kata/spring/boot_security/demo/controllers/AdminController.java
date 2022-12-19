package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;


    @Autowired
    public AdminController(UserServiceImpl userService, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @GetMapping()
    public String index(Model model) {
        System.out.println(userService.index());
        model.addAttribute("user", userService.getUserOfAuthentication());
        model.addAttribute("users", userService.index());
        model.addAttribute("roleList", roleService.findAll());
        return "admin/admin_page_bt";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) { // С помощью анотации @PathVariable Мы получим id c в метод
        model.addAttribute("user", userService.show(id));
        return "admin/show";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roleList", roleService.findAll());
        return "admin/new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("user") User user,
                         @RequestParam("str") String[] rolesOfForm) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(roleService.setUserRole(user, rolesOfForm));
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id,@ModelAttribute("roleTh") Role role) {
        model.addAttribute("roleList", roleService.findAll());
        model.addAttribute("user", userService.show(id));
        return "admin/edit";
    }

    @PatchMapping()
    public String update(@ModelAttribute("user") User user,
                         @RequestParam("str") String[] rolesOfForm) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.updateUser(user.getId(), roleService.setUserRole(user, rolesOfForm));
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id, Model model) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}