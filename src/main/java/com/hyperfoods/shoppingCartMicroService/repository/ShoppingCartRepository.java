package com.hyperfoods.shoppingCartMicroService.repository;

import com.hyperfoods.shoppingCartMicroService.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

}
