package com.teamtwo.stocko_supply.controller.dashboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.teamtwo.stocko_supply.models.User;
import com.teamtwo.stocko_supply.repository.UserRepository;
import com.teamtwo.stocko_supply.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/dashboard/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String listUsers(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (!"admin".equalsIgnoreCase(currentUser.getRole())) {
            model.addAttribute("error", "Anda tidak dapat mengakses url ini");
            return "redirect:/dashboard";
        }

        boolean isAdmin = "admin".equalsIgnoreCase(currentUser.getRole());
        List<User> users = userRepository.findAllByOrderByIdAsc();

        model.addAttribute("users", users);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("isAdmin", isAdmin);

        return "dashboard/users/index";
    }

    @GetMapping("/add")
    public String addUserForm(HttpServletRequest request) {
        // Check if user is admin
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || !currentUser.isAdmin()) {
            return "redirect:/dashboard";
        }

        return "dashboard/users/index";
    }

    @PostMapping("/add")
    public String addUser(@RequestParam String username,
            @RequestParam String password,
            @RequestParam String role,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {
        // Check if user is admin
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || !currentUser.isAdmin()) {
            return "redirect:/dashboard";
        }

        model.addAttribute("error", "Username sudah digunakan");
        return "dashboard/users/index";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id,
            Model model,
            HttpServletRequest request) {
        // Check if user is admin
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || !currentUser.isAdmin()) {
            return "redirect:/dashboard";
        }

        User user = userService.getUserById(id);
        if (user == null) {
            return "redirect:/dashboard/users/index";
        }

        model.addAttribute("user", user);
        return "dashboard/users/index";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable Long id,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String role,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {
        // Check if user is admin
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || !currentUser.isAdmin()) {
            return "redirect:/dashboard";
        }

        if (userService.updateUser(id, username, password, role)) {
            redirectAttributes.addFlashAttribute("success", "User berhasil diperbarui!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Gagal memperbarui user");
        }
        return "redirect:/dashboard/users/index";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {
        // Check if user is admin
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || !currentUser.isAdmin()) {
            return "redirect:/dashboard";
        }

        if (userService.deleteUser(id)) {
            redirectAttributes.addFlashAttribute("success", "User berhasil dihapus!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Gagal menghapus user");
        }
        return "redirect:/dashboard/users/index";
    }

    @GetMapping("/search")
    public String searchUsers(@RequestParam("keyword") String keyword, Model model, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute("currentUser");

        if (currentUser == null || !"admin".equalsIgnoreCase(currentUser.getRole())) {
            model.addAttribute("error", "Tidak memiliki akses");
            return "redirect:/dashboard";
        }

        List<User> users = userRepository.findByUsernameContainingIgnoreCase(keyword);

        model.addAttribute("users", users);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("isAdmin", true);
        model.addAttribute("keyword", keyword);

        return "dashboard/users/index";
    }
}
