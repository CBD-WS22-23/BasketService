package edu.timebandit.BasketService.port.user.controller;

import edu.timebandit.BasketService.core.domain.model.Basket;
import edu.timebandit.BasketService.core.domain.service.interfaces.IBasketProductService;
import edu.timebandit.BasketService.core.domain.service.interfaces.IBasketService;
import edu.timebandit.BasketService.port.user.exception.BasketNotFoundException;
import edu.timebandit.BasketService.port.user.exception.BasketProductNotFoundException;
import edu.timebandit.BasketService.port.user.exception.InvalidQuantityException;
import edu.timebandit.BasketService.port.user.exception.ProductNotInBasketException;
import edu.timebandit.BasketService.port.user.producer.BasketProducer;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class BasketController {

    @Autowired
    private IBasketService basketService;

    @Autowired
    private IBasketProductService basketProductService;

    @Autowired
    private BasketProducer basketProducer;


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
            basketService.clearBasket(basketID);
            return "Basket with id: " + basketID + " was cleared";
        } else {
            throw new BasketNotFoundException(basketID);
        }
    }

    @Operation(summary = "Add a product to a basket")
    @PostMapping(path = "/basket/{basketID}/product/{productID}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Double addProductToBasket(@PathVariable String basketID, @PathVariable String productID,
                                     @RequestParam int q) {
        if (q <= 0) {
            throw new InvalidQuantityException();
        }
        if (!basketProductService.checkIfBasketProductExists(productID)){
            throw new BasketProductNotFoundException(productID);
        }
        Double totalPrice = basketService.addProductToBasket(basketID, basketProductService.getBasketProductByID(productID), q);
        if (totalPrice == null) {
            throw new BasketNotFoundException(basketID);
        }
        basketProducer.sendProductAddedToBasketMessage(productID);
        return totalPrice;
    }

    @Operation(summary = "Remove a product from a basket")
    @DeleteMapping(path = "/basket/{basketID}/product/{productID}")
    @ResponseStatus(HttpStatus.OK)
    public Double removeProductFromBasket(@PathVariable String basketID, @PathVariable String productID) {
        Double totalPrice = basketService.removeProductFromBasket(basketID, productID);
        if (totalPrice == null) {
            throw new BasketNotFoundException(basketID);
        }
        basketProducer.sendProductRemovedFromBasketMessage(productID);
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
        if (!basketProductService.checkIfBasketProductExists(productID)){
            throw new BasketProductNotFoundException(productID);
        }
        Double totalPrice = basketService.updateProductQuantity(basketID, basketProductService.getBasketProductByID(productID), q);
        if (totalPrice == null) {
            throw new BasketNotFoundException(basketID);
        }
        if (totalPrice == -1.0) {
            throw new ProductNotInBasketException(basketID, productID);
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
        if (totalPrice == -1.0) {
            throw new ProductNotInBasketException(basketID, productID);
        }
        return totalPrice;
    }
}
