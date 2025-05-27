package com.teamtwo.stocko_supply.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.teamtwo.stocko_supply.models.Barang;
import com.teamtwo.stocko_supply.models.User;
import com.teamtwo.stocko_supply.repository.UserRepository;
import com.teamtwo.stocko_supply.service.BarangService;
import com.teamtwo.stocko_supply.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BarangService barangService;

    // Static map to track online users (in production, use Redis or database)
    private static final Map<Long, Long> onlineUsers = new HashMap<>();
    private static final long ONLINE_TIMEOUT = 5 * 60 * 1000; // 5 minutes

    @GetMapping("")
    public String showDashboard(
            Model model,
            HttpServletRequest request,
            @RequestParam(value = "sort", required = false, defaultValue = "nama") String sort,
            @RequestParam(value = "order", required = false, defaultValue = "asc") String order) {
        try {
            // First check if user is authenticated
            User currentUser = userService.getCurrentUser(request);
            if (currentUser == null) {
                throw new Error("currentUser is null");
            }

            // Update current user's last activity
            updateUserActivity(currentUser.getId());

            boolean isAdmin = currentUser != null && "admin".equalsIgnoreCase(currentUser.getUsername())
                    && "admin".equalsIgnoreCase(currentUser.getRole());
            List<User> users = userRepository.findAllByOrderByIdAsc();

            // Create user status map with online status
            Map<Long, Boolean> userOnlineStatus = new HashMap<>();
            long currentTime = System.currentTimeMillis();

            for (User user : users) {
                Long lastActivity = onlineUsers.get(user.getId());
                boolean isOnline = lastActivity != null && (currentTime - lastActivity) < ONLINE_TIMEOUT;
                userOnlineStatus.put(user.getId(), isOnline);
            }

            // Total Barang & Pengguna
            long totalBarang = barangService.countAllBarang();
            long totalUsers = userService.countAllUsers();

            // Barang hari ini
            long totalBarangHariIni = barangService.countBarangHariIni();
            List<Barang> barangHariIni = barangService.getBarangHariIni();

            // Sortable table logic
            Comparator<Barang> comparator;
            switch (sort) {
                case "status":
                    comparator = Comparator.comparing(b -> b.getJumlah() > 0 ? 1 : 0);
                    break;
                case "desc":
                    comparator = Comparator.comparing(Barang::getNama, String.CASE_INSENSITIVE_ORDER).reversed();
                    break;
                case "nama":
                default:
                    comparator = Comparator.comparing(Barang::getNama, String.CASE_INSENSITIVE_ORDER);
                    break;
            }
            if ("desc".equals(order) && !"desc".equals(sort)) {
                comparator = comparator.reversed();
            }
            barangHariIni.sort(comparator);

            model.addAttribute("users", users);
            model.addAttribute("userOnlineStatus", userOnlineStatus);
            model.addAttribute("isAdmin", isAdmin);
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("totalBarang", totalBarang);
            model.addAttribute("totalUsers", totalUsers);
            model.addAttribute("totalBarangHariIni", totalBarangHariIni);
            model.addAttribute("barangHariIni", barangHariIni);
            model.addAttribute("sort", sort);
            model.addAttribute("order", order);

            return "dashboard/index";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/auth/login"; // Redirect to login if any error occurs
        }
    }

    private void updateUserActivity(Long userId) {
        onlineUsers.put(userId, System.currentTimeMillis());
    }
}
