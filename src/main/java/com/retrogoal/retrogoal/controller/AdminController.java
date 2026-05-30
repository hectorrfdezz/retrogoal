package com.retrogoal.retrogoal.controller;

import com.retrogoal.retrogoal.model.Order;
import com.retrogoal.retrogoal.model.OrderStatus;
import com.retrogoal.retrogoal.model.Product;
import com.retrogoal.retrogoal.model.User;
import com.retrogoal.retrogoal.repository.UserRepository;
import com.retrogoal.retrogoal.service.OrderService;
import com.retrogoal.retrogoal.service.ProductService;
import com.retrogoal.retrogoal.service.ProductAutoTranslationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Administration interface for managing products and orders.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final ProductService productService;
    private final ProductAutoTranslationService productAutoTranslationService;
    private final OrderService orderService;
    private final UserRepository userRepository;

    @GetMapping({"", "/dashboard"})
    public String adminHome(Model model) {
        List<Product> products = productService.findAll();
        List<Order> orders = orderService.findAllOrders();
        List<User> users = userRepository.findAll();
        List<User> admins = users.stream()
                .filter(user -> user.getRoles() != null && user.getRoles().stream()
                        .anyMatch(role -> "ROLE_ADMIN".equals(role.getName())))
                .collect(Collectors.toList());
        long totalStock = products.stream().mapToLong(Product::getStock).sum();
        BigDecimal totalSales = orders.stream()
                .filter(order -> order.getTotalPrice() != null)
                .map(Order::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("products", products);
        model.addAttribute("orders", orders);
        model.addAttribute("users", users);
        model.addAttribute("admins", admins);
        model.addAttribute("totalProducts", products.size());
        model.addAttribute("totalOrders", orders.size());
        model.addAttribute("totalUsers", users.size());
        model.addAttribute("totalAdmins", admins.size());
        model.addAttribute("totalStock", totalStock);
        model.addAttribute("totalSales", totalSales);
        return "admin/dashboard";
    }

    /*--- Product management ---*/

    @GetMapping("/products")
    public String productsRedirect() {
        return "redirect:/admin";
    }

    @GetMapping("/products/new")
    public String showProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "admin/productForm";
    }

    @PostMapping({"/products", "/products/new"})
    public String saveProduct(@ModelAttribute("product") @Valid Product product,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "admin/productForm";
        }
        productAutoTranslationService.autoTranslate(product);
        productService.save(product);
        return "redirect:/admin";
    }

    @PostMapping("/products/edit/{id}")
    public String saveEditedProduct(@PathVariable Long id,
                                    @ModelAttribute("product") @Valid Product product,
                                    BindingResult result) {
        if (result.hasErrors()) {
            product.setId(id);
            return "admin/productForm";
        }
        product.setId(id);
        productAutoTranslationService.autoTranslate(product);
        productService.save(product);
        return "redirect:/admin";
    }

    @GetMapping("/products/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);
        if (product == null) {
            return "redirect:/admin";
        }
        model.addAttribute("product", product);
        return "admin/productForm";
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/admin";
    }

    /*--- Order management ---*/

    @GetMapping("/orders/{id}")
    public String viewOrder(@PathVariable Long id, Model model) {
        Order order = orderService.findById(id);
        if (order == null) {
            return "redirect:/admin";
        }
        model.addAttribute("order", order);
        model.addAttribute("statuses", OrderStatus.values());
        return "admin/orderDetail";
    }

    @PostMapping("/orders/{id}")
    public String updateOrderStatus(@PathVariable Long id,
                                    @RequestParam OrderStatus status) {
        orderService.updateStatus(id, status);
        return "redirect:/admin/orders/" + id;
    }
}