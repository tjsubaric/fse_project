package com.uiowa.fse_project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uiowa.fse_project.model.Admin;
import com.uiowa.fse_project.model.UserDtls;
import com.uiowa.fse_project.service.AdminService;
import com.uiowa.fse_project.service.UserService;

@RestController
@RequestMapping("/api/admins")
public class AdminRestController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public List<Admin> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    @GetMapping("/{id}")
    public Admin getAdminById(@PathVariable long id) {
        return adminService.getAdminById(id);
    }

    @GetMapping("/users")
    public List<UserDtls> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/saveAdmin")
    public void saveAdmin(@RequestBody Admin admin) {
        adminService.saveAdmin(admin);
    }

    @PutMapping("/updateAdmin/{id}")
    public void updateAdmin(@PathVariable long id, @RequestBody Admin admin) {
        Admin existingAdmin = adminService.getAdminById(id);
        existingAdmin.setEmail(admin.getEmail());
        existingAdmin.setRole(admin.getRole());
        adminService.saveAdmin(existingAdmin);
    }

    @DeleteMapping("deleteAdmin/{id}")
    public void deleteAdmin(@PathVariable long id) {
        adminService.deleteAdminById(id);
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;

        Page<Admin> page = adminService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Admin> admins = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("admins", admins);
        return "home";
    }
}
