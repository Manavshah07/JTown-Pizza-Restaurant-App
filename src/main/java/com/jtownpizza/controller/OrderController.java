package com.jtownpizza.controller;

import com.jtownpizza.dto.CartItem;
import com.jtownpizza.model.Order;
import com.jtownpizza.model.OrderItem;
import com.jtownpizza.repository.OrderRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {
    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/checkout")
    public String checkout(Model model, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();
        double total = cart.stream().mapToDouble(i -> i.getPrice() * i.getQty()).sum();
        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        return "checkout";
    }

    @PostMapping("/checkout")
    public String placeOrder(@RequestParam String name, @RequestParam String email, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) return "redirect:/menu";

        List<OrderItem> items = new ArrayList<>();
        double total = 0;
        for (CartItem ci : cart) {
            items.add(OrderItem.builder().menuItemId(ci.getId()).name(ci.getName()).qty(ci.getQty()).price(ci.getPrice()).build());
            total += ci.getQty() * ci.getPrice();
        }

        Order order = Order.builder()
                .customerName(name)
                .customerEmail(email)
                .createdAt(LocalDateTime.now())
                .items(items)
                .total(total)
                .build();

        orderRepository.save(order);
        session.removeAttribute("cart");
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String listOrders(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        return "orders";
    }
}
