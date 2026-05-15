package com.retrogoal.retrogoal.controller;

import com.retrogoal.retrogoal.model.Order;
import com.retrogoal.retrogoal.model.OrderStatus;
import com.retrogoal.retrogoal.model.Product;
import com.retrogoal.retrogoal.service.OrderService;
import com.retrogoal.retrogoal.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Administration interface for managing products and orders.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final ProductService productService;
    private final OrderService orderService;

    @GetMapping
    public String adminHome(Model model) {
        List<Product> products = productService.findAll();
        List<Order> orders = orderService.findAllOrders();
        model.addAttribute("products", products);
        model.addAttribute("orders", orders);
        return "admin/dashboard";
    }

    /*--- Product management ---*/

    @GetMapping("/products/new")
    public String showProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "admin/productForm";
    }

    @PostMapping("/products")
    public String saveProduct(@ModelAttribute("product") @Valid Product product,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "admin/productForm";
        }
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