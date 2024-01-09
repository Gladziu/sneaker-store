package com.rafal.sneakerstoreapp.order.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    private String address;
    private String postCode;
    private String city;
    private LocalDateTime created;
    private BigDecimal sum;
    private String email;

    @OneToMany(mappedBy = "orderHistory", cascade = CascadeType.ALL)
    private List<OrderItem> orderItem;
}
