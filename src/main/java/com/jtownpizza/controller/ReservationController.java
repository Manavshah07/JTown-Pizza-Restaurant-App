package com.jtownpizza.controller;

import com.jtownpizza.model.Reservation;
import com.jtownpizza.repository.ReservationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class ReservationController {
    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservations")
    public String reservations(Model model) {
        model.addAttribute("reservations", reservationRepository.findAll());
        return "reservations";
    }

    @PostMapping("/reservations")
    public String makeReservation(@RequestParam String name, @RequestParam String email, @RequestParam Integer partySize, @RequestParam String datetime) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dt = LocalDateTime.parse(datetime, fmt);
        Reservation r = Reservation.builder().name(name).email(email).partySize(partySize).reservationTime(dt).build();
        reservationRepository.save(r);
        return "redirect:/reservations";
    }
}
