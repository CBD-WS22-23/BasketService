package edu.timebandit.BasketService.port.checkout.controller;

import edu.timebandit.BasketService.core.domain.model.Basket;
import edu.timebandit.BasketService.core.domain.model.Watch;
import edu.timebandit.BasketService.core.domain.service.interfaces.IBasketService;
import edu.timebandit.BasketService.port.checkout.dtos.*;
import edu.timebandit.BasketService.port.checkout.enums.PaymentMethod;
import edu.timebandit.BasketService.port.checkout.producer.CheckoutOrderProducer;
import edu.timebandit.BasketService.port.user.exception.BasketNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class CheckoutBasketController {

    @Autowired
    private IBasketService basketService;

    @Autowired
    private CheckoutOrderProducer checkoutOrderProducer;


    @Operation(summary = "Checkout a basket")
    @PostMapping(path = "/basket/{basketID}/checkout")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void checkoutBasket(@PathVariable String basketID, @RequestParam PaymentMethod paymentMethod,
                               @RequestBody @Valid AddressDataDTO userAddresses) {
        if (basketService.checkBasketExists(basketID)) {
            Basket basket = basketService.getBasketByID(basketID);
            List<WatchDTO> checkoutProducts = new ArrayList<>();
            Map<Watch, Integer> productsMap = basket.getProducts();
            for (Watch product : productsMap.keySet()) {
                checkoutProducts.add(new WatchDTO(product.getId().toString(), product.getName(), product.getPrice(),
                        productsMap.get(product)));
            }
            BasketDTO basketDTO = new BasketDTO(basket.getId().toString(), checkoutProducts, basket.getTotalPrice());

            checkoutOrderProducer.sendCheckoutOrderMessage(new CheckoutDTO( basketDTO,
                    userAddresses.getShippingAddress(), userAddresses.getBillingAddress(), paymentMethod.toString()));
        } else {
            throw new BasketNotFoundException(basketID);
        }
    }
}
