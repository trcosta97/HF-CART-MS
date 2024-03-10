package com.hyperfoods.shoppingCartMicroService.service;

import com.hyperfoods.shoppingCartMicroService.entity.ShoppingCart;
import com.hyperfoods.shoppingCartMicroService.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    private String foodUrl = "http://localhost:8084/food/price";

    public ShoppingCart create(ShoppingCart shoppingCart) {
        shoppingCart.setCreated(LocalDateTime.now());
        shoppingCart.setActive(true);
        shoppingCart.setPrice(BigDecimal.ZERO);
        shoppingCart.setFoods(new ArrayList<>());

        return shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCart addFood(Long foodId, Long shoppingCartId) {
        BigDecimal foodPrice = new RestTemplate().getForObject(foodUrl + foodId, BigDecimal.class);

        Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findById(shoppingCartId);
        if (shoppingCartOptional.isPresent()) {
            ShoppingCart shoppingCart = shoppingCartOptional.get();
            shoppingCart.getFoods().add(foodId);

            BigDecimal currentPrice = shoppingCart.getPrice();
            if (currentPrice == null) {
                currentPrice = BigDecimal.ZERO;
            }
            currentPrice = currentPrice.add(foodPrice);
            shoppingCart.setPrice(currentPrice);

            return shoppingCartRepository.save(shoppingCart);
        }
        return null;
    }


    public ShoppingCart removeFood(Long foodId, Long shoppingCartId) {
        BigDecimal foodPrice = new RestTemplate().getForObject(foodUrl + foodId, BigDecimal.class);

        Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findById(shoppingCartId);
        if (shoppingCartOptional.isPresent()) {
            ShoppingCart shoppingCart = shoppingCartOptional.get();
            shoppingCart.getFoods().remove(foodId);
            BigDecimal currentPrice = shoppingCart.getPrice();
            if (currentPrice == null) {
                currentPrice = BigDecimal.ZERO;
            }
            currentPrice = currentPrice.subtract(foodPrice);
            shoppingCart.setPrice(currentPrice);

            return shoppingCartRepository.save(shoppingCart);
        }
        return null;
    }

    public void deactivateShoppingCart(Long id) {
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findById(id);
        if(optionalShoppingCart.isPresent()) {
            ShoppingCart shoppingCart = optionalShoppingCart.get();
            shoppingCart.setActive(false);
            shoppingCartRepository.save(shoppingCart);
        }

    }
}
