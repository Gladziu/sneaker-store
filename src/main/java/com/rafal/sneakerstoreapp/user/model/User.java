package com.rafal.sneakerstoreapp.user.model;

import com.rafal.sneakerstoreapp.shoppingbasket.model.BasketInfo;
import com.rafal.sneakerstoreapp.shoppingbasket.model.BasketItem;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<BasketItem> basketItem = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private BasketInfo basketInfo;

}