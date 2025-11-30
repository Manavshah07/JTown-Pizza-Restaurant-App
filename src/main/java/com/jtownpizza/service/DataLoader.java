package com.jtownpizza.service;

import com.jtownpizza.model.MenuItem;
import com.jtownpizza.model.User;
import com.jtownpizza.repository.MenuItemRepository;
import com.jtownpizza.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final MenuItemRepository menuItemRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(MenuItemRepository menuItemRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.menuItemRepository = menuItemRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (menuItemRepository.count() == 0) {
            menuItemRepository.save(MenuItem.builder().name("Margherita Pizza").description("Classic cheese pizza").price(6.99).category("Pizza").build());
            menuItemRepository.save(MenuItem.builder().name("Pepperoni Pizza").description("Pepperoni and cheese").price(8.99).category("Pizza").build());
            menuItemRepository.save(MenuItem.builder().name("Veggie Pizza").description("Peppers, olives, onions").price(7.99).category("Pizza").build());
            menuItemRepository.save(MenuItem.builder().name("Garlic Bread").description("Toasted garlic bread").price(3.49).category("Sides").build());
            menuItemRepository.save(MenuItem.builder().name("Coke").description("330ml").price(1.49).category("Drinks").build());
        }

        if (userRepository.count() == 0) {
            userRepository.save(User.builder().email("admin@jtown.com").fullName("Admin").password(passwordEncoder.encode("adminpass")).role("ROLE_ADMIN").build());
            userRepository.save(User.builder().email("user@jtown.com").fullName("Demo User").password(passwordEncoder.encode("userpass")).role("ROLE_USER").build());
        }
    }
}
