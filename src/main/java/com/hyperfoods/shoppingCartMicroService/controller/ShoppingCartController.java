package com.hyperfoods.shoppingCartMicroService.controller;

import com.hyperfoods.shoppingCartMicroService.dto.CreateShoppingCartDTO;
import com.hyperfoods.shoppingCartMicroService.entity.ShoppingCart;
import com.hyperfoods.shoppingCartMicroService.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

        @Autowired
        private ShoppingCartService service;

        public ResponseEntity create(CreateShoppingCartDTO data, UriComponentsBuilder uriComponentsBuilder) {
            ShoppingCart shoppingCart = service.create(new ShoppingCart(data));
            return ResponseEntity.created(uriComponentsBuilder.path("/shoppingCart/{id}").buildAndExpand(shoppingCart.getId()).toUri()).build();
        }

        public ResponseEntity addFood(Long foodId, Long shoppingCartId) {
            return ResponseEntity.ok(service.addFood(foodId, shoppingCartId));
        }

        public ResponseEntity removeFood(Long foodId, Long shoppingCartId) {
            return ResponseEntity.ok(service.removeFood(foodId, shoppingCartId));
        }

}
