package edu.timebandit.BasketService.port.user.controller;

import edu.timebandit.BasketService.core.domain.model.Basket;
import edu.timebandit.BasketService.core.domain.service.interfaces.IBasketService;
import edu.timebandit.BasketService.port.user.exception.BasketNotFoundException;
import edu.timebandit.BasketService.port.user.exception.InvalidQuantityException;
import edu.timebandit.BasketService.port.user.producer.interfaces.IProductRemovedFromBasketProducer;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bs/api/v1")
public class UserBasketController {

    @Autowired
    private IBasketService basketService;

    @Autowired
    private IProductRemovedFromBasketProducer productRemovedFromBasketProducer;


    @Operation(summary = "Create a new basket")
    @PostMapping(path = "/basket")
    @ResponseStatus(HttpStatus.CREATED)
    public String createBasket() {
        return basketService.createBasket();
    }


    @Operation(summary = "Get a basket by id")
    @GetMapping(path = "/basket/{basketID}")
    public Basket getBasket(@PathVariable String basketID) {
        if (basketService.checkBasketExists(basketID)) {
            return basketService.getBasketByID(basketID);
        } else {
            throw new BasketNotFoundException(basketID);
        }
    }

    @Operation(summary = "Get all baskets")
    @GetMapping(path = "/basket")
    public Iterable<Basket> getAllBaskets() {
        return basketService.getAllBaskets();
    }

    @Operation(summary = "Delete a basket by id")
    @DeleteMapping(path = "/basket/{basketID}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBasket(@PathVariable String basketID) {
        basketService.deleteBasket(basketID);
    }

    @Operation(summary = "Get the total price of a basket")
    @GetMapping(path = "/basket/{basketID}/total")
    public Double getTotalPrice(@PathVariable String basketID) {
        Double totalPrice = basketService.getBasketTotalPrice(basketID);
        if (totalPrice == null) {
            throw new BasketNotFoundException(basketID);
        }
        return totalPrice;
    }

    @Operation(summary = "Clear a basket")
    @DeleteMapping(path = "/basket/{basketID}/clear")
    @ResponseStatus(HttpStatus.OK)
    public String clearBasket(@PathVariable String basketID) {
        if (basketService.checkBasketExists(basketID)) {
            basketService.getBasketByID(basketID).getProducts().forEach((product, amount) -> productRemovedFromBasketProducer.sendProductRemovedFromBasketMessage(product.getId().toString()));
            basketService.clearBasket(basketID);
            return "Basket with id: " + basketID + " was cleared";
        } else {
            throw new BasketNotFoundException(basketID);
        }
    }

    @Operation(summary = "Remove a product from a basket")
    @DeleteMapping(path = "/basket/{basketID}/products/")
    @ResponseStatus(HttpStatus.OK)
    public Double removeProductFromBasket(@PathVariable String basketID, @RequestParam String productID) {
        Double totalPrice = basketService.removeProductFromBasket(basketID, productID);
        if (totalPrice == null) {
            throw new BasketNotFoundException(basketID);
        }
        productRemovedFromBasketProducer.sendProductRemovedFromBasketMessage(productID);
        return totalPrice;
    }

    @Operation(summary = "Update the quantity of a product in a basket")
    @PutMapping(path = "/basket/{basketID}/product/{productID}")
    @ResponseStatus(HttpStatus.OK)
    public Double updateProductQuantity(@PathVariable String basketID, @PathVariable String productID,
                                        @RequestParam int q) {
        if (q <= 0) {
            throw new InvalidQuantityException();
        }
        Double totalPrice = basketService.updateProductQuantity(basketID, productID, q);
        if (totalPrice == null) {
            throw new BasketNotFoundException(basketID);
        }
        return totalPrice;
    }

    @Operation(summary = "Get the total price of a product in a basket")
    @GetMapping(path = "/basket/{basketID}/product/{productID}/total")
    @ResponseStatus(HttpStatus.OK)
    public Double getProductTotalPrice(@PathVariable String basketID, @PathVariable String productID) {
        Double totalPrice = basketService.getProductTotalPrice(basketID, productID);
        if (totalPrice == null) {
            throw new BasketNotFoundException(basketID);
        }
        return totalPrice;
    }
}
