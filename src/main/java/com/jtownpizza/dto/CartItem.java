package com.jtownpizza.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    private Long id;
    private String name;
    private Double price;
    private Integer qty;
}
