package com.teamtwo.stocko_supply.controller.dashboard;

import java.time.LocalDateTime;
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

import com.teamtwo.stocko_supply.models.Barang;
import com.teamtwo.stocko_supply.models.User;
import com.teamtwo.stocko_supply.repository.BarangRepository;
import com.teamtwo.stocko_supply.service.BarangService;
import com.teamtwo.stocko_supply.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/dashboard/barang")
public class BarangController {
    @Autowired
    private BarangRepository barangRepository;

    @Autowired
    private BarangService barangService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String listBarang(Model model, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser(request);
        if (currentUser == null) {
            return "redirect:/auth/login";
        }

        List<Barang> barangs = barangRepository.findAllByOrderByIdAsc();

        model.addAttribute("barangs", barangs);
        model.addAttribute("currentUser", currentUser);

        return "/dashboard/barang/index";
    }

    @PostMapping("/add")
    public String addBarang(@RequestParam String nama,
            @RequestParam String kategori,
            @RequestParam Integer jumlah,
            @RequestParam String keterangan,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request, Model model) {
        User currentUser = userService.getCurrentUser(request);

        model.addAttribute("currentUser", currentUser);

        if (barangService.addNewBarang(nama, kategori, jumlah, keterangan,
                LocalDateTime.now())) {
            redirectAttributes.addFlashAttribute("success", "Barang berhasil diperbarui");
        } else {
            redirectAttributes.addFlashAttribute("error", "Gagal memperbarui barang!");
        }

        return "redirect:/dashboard/barang";
    }

    @GetMapping("/edit/{id}")
    public String editBarangForm(@PathVariable Long id) {
        return "redirect:/dashboard/barang";
    }

    @PostMapping("/edit/{id}")
    public String editBarang(@PathVariable Long id,
            @RequestParam String nama,
            @RequestParam String kategori,
            @RequestParam Integer jumlah,
            @RequestParam String keterangan,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request, Model model) {
        User currentUser = userService.getCurrentUser(request);

        model.addAttribute("currentUser", currentUser);

        if (barangService.updateBarang(id, nama, kategori, jumlah, keterangan)) {
            redirectAttributes.addFlashAttribute("success", "Barang berhasil diperbarui");
        } else {
            redirectAttributes.addFlashAttribute("error", "Gagal memperbarui barang!");
        }

        return "redirect:/dashboard/barang";
    }

    @GetMapping("/delete/{id}")
    public String deleteUserForm(@PathVariable Long id) {
        return "redirect:/dashboard/barang";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, Model model,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {
        // Check if user is admin
        User currentUser = userService.getCurrentUser(request);

        model.addAttribute("currentUser", currentUser);

        if (barangService.deleteBarang(id)) {
            redirectAttributes.addFlashAttribute("success", "User berhasil dihapus!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Gagal menghapus user");
        }

        return "redirect:/dashboard/barang";
    }

    @GetMapping("/search")
    public String searchUsers(@RequestParam("keyword") String keyword, Model model, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser(request);
        List<Barang> barang = barangRepository.findByNamaContainingIgnoreCase(keyword);

        model.addAttribute("barangs", barang);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("keyword", keyword);

        return "dashboard/barang/index";
    }
}
