package com.jtownpizza.controller;

import com.jtownpizza.dto.CartItem;
import com.jtownpizza.model.MenuItem;
import com.jtownpizza.repository.MenuItemRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CartController {
    private final MenuItemRepository menuItemRepository;

    public CartController(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @PostMapping("/cart/add/{id}")
    public String addToCart(@PathVariable Long id, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();

        Optional<MenuItem> mi = menuItemRepository.findById(id);
        if (mi.isPresent()) {
            MenuItem menuItem = mi.get();
            boolean found = false;
            for (CartItem ci : cart) {
                if (ci.getId().equals(menuItem.getId())) {
                    ci.setQty(ci.getQty() + 1);
                    found = true;
                    break;
                }
            }
            if (!found) {
                cart.add(new CartItem(menuItem.getId(), menuItem.getName(), menuItem.getPrice(), 1));
            }
        }

        session.setAttribute("cart", cart);
        return "redirect:/menu";
    }

    @GetMapping("/cart")
    public String viewCart(Model model, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();
        double total = cart.stream().mapToDouble(i -> i.getPrice() * i.getQty()).sum();
        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        return "cart";
    }

    @PostMapping("/cart/update")
    public String updateCart(HttpSession session, Integer[] qty, Long[] ids) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) return "redirect:/cart";
        for (int i = 0; i < ids.length; i++) {
            for (CartItem ci : cart) {
                if (ci.getId().equals(ids[i])) ci.setQty(qty[i]);
            }
        }
        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

    @PostMapping("/cart/clear")
    public String clearCart(HttpSession session) {
        session.removeAttribute("cart");
        return "redirect:/menu";
    }
}
