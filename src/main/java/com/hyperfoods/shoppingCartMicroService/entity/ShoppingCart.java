package com.hyperfoods.shoppingCartMicroService.entity;

import com.hyperfoods.shoppingCartMicroService.dto.CreateShoppingCartDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "ShoppingCart")
@Table(name = "tab_shopping_cart")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "shopping_cart_id")
    private long id;
    @ElementCollection
    @CollectionTable(name = "shopping_cart_foods", joinColumns = @JoinColumn(name = "shopping_cart_id"))
    @Column(name = "food_id")
    private List<Long> foods;
    private Long userId;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "is_active")
    private boolean active = true;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime created;

    public ShoppingCart(CreateShoppingCartDTO data) {
        this.userId = data.userId();
    }
}
