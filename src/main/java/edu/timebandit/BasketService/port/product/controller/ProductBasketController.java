package edu.timebandit.BasketService.port.product.controller;

import edu.timebandit.BasketService.core.domain.model.Watch;
import edu.timebandit.BasketService.core.domain.service.interfaces.IBasketService;

import edu.timebandit.BasketService.port.product.producer.ProductBasketProducer;

import edu.timebandit.BasketService.port.user.exception.BasketNotFoundException;
import edu.timebandit.BasketService.port.user.exception.InvalidQuantityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
public class ProductBasketController {

    @Autowired
    private IBasketService basketService;

    @Autowired
    private ProductBasketProducer productBasketProducer;

    public void addProductToBasket(String basketID, Watch product, int q) {
        if (q <= 0) {
            throw new InvalidQuantityException();
        }

        Double totalPrice = basketService.addProductToBasket(basketID, product, q);
        if (totalPrice == null) {
            throw new BasketNotFoundException(basketID);
        }
        productBasketProducer.sendProductAddedToBasketMessage(product.getId().toString());
    }
}
