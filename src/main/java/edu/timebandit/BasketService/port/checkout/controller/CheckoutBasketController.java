package edu.timebandit.BasketService.port.checkout.controller;

import edu.timebandit.BasketService.core.domain.service.interfaces.IBasketService;
import edu.timebandit.BasketService.port.checkout.dtos.AddressDTO;
import edu.timebandit.BasketService.port.checkout.dtos.CheckoutDTO;
import edu.timebandit.BasketService.port.checkout.enums.PaymentMethod;
import edu.timebandit.BasketService.port.checkout.producer.CheckoutOrderProducer;
import edu.timebandit.BasketService.port.user.exception.BasketNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public void checkoutBasket(@PathVariable String basketID, @Valid AddressDTO shippingAddress,
                               @Valid AddressDTO billingAddress,
                               @RequestParam PaymentMethod paymentMethod) {
        if (basketService.checkBasketExists(basketID)) {
            checkoutOrderProducer.sendCheckoutOrderMessage(new CheckoutDTO(basketService.getBasketByID(basketID),
                    shippingAddress, billingAddress, paymentMethod.toString()));
        } else {
            throw new BasketNotFoundException(basketID);
        }
    }
}
