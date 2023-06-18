package edu.timebandit.BasketService.port.user.controller;

import edu.timebandit.BasketService.core.domain.model.Basket;
import edu.timebandit.BasketService.core.domain.service.interfaces.IBasketProductService;
import edu.timebandit.BasketService.core.domain.service.interfaces.IBasketService;
import edu.timebandit.BasketService.port.user.exception.BasketNotFoundException;
import edu.timebandit.BasketService.port.user.exception.BasketProductNotFoundException;
import edu.timebandit.BasketService.port.user.exception.InvalidQuantityException;
import edu.timebandit.BasketService.port.user.exception.ProductNotInBasketException;
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
    public String deleteBasket(@PathVariable String basketID) {
        if (basketService.checkBasketExists(basketID)) {
            basketService.deleteBasket(basketID);
            return "Basket with id: " + basketID + " was deleted";
        } else {
            throw new BasketNotFoundException(basketID);
        }
    }


    @Operation(summary = "Get the total price of a basket")
    @GetMapping(path = "/basket/{basketID}/total")
    public Double getTotalPrice(@PathVariable String basketID) {
        if (basketService.checkBasketExists(basketID)) {
            return basketService.getBasketTotalPrice(basketID);
        } else {
            throw new BasketNotFoundException(basketID);
        }
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
        if (basketService.checkBasketExists(basketID)) {
            if (basketProductService.checkIfBasketProductExists(productID)) {
                if (q > 0) {
                    return basketService.addProductToBasket(basketID, basketProductService.getBasketProductByID(productID), q);
                } else {
                    throw new InvalidQuantityException();
                }
            } else {
                throw new BasketProductNotFoundException(productID);
            }
        } else {
            throw new BasketNotFoundException(basketID);
        }
    }

    /*
    @Operation(summary = "Get an item in the basket by id")
    @GetMapping(path = "/basket/{basketID}/product/{productID}")
    @ResponseStatus(HttpStatus.OK)
    public BasketWatch getBasketItem(@PathVariable String basketID, @PathVariable String productID) {
        if (basketService.checkBasketExists(basketID)) {
            BasketWatch basketWatch = basketService.getBasketItem(basketID, productID);
            if (basketWatch != null) {
                return basketWatch;
            } else {
                throw new ProductNotInBasketException(basketID, productID);
            }
        } else {
            throw new BasketNotFoundException(basketID);
        }
    }
     */

    @Operation(summary = "Remove a product from a basket")
    @DeleteMapping(path = "/basket/{basketID}/product/{productID}")
    @ResponseStatus(HttpStatus.OK)
    public Double removeProductFromBasket(@PathVariable String basketID, @PathVariable String productID) {
        return null;
    }

    @Operation(summary = "Update the quantity of a product in a basket")
    @PutMapping(path = "/basket/{basketID}/product/{productID}")
    @ResponseStatus(HttpStatus.OK)
    public Double updateProductQuantity(@PathVariable String basketID, @PathVariable String productID,
                                        @RequestParam int q) {
        return null;
    }

    @Operation(summary = "Get the total price of a product in a basket")
    @GetMapping(path = "/basket/{basketID}/product/{productID}/total")
    @ResponseStatus(HttpStatus.OK)
    public Double getProductTotalPrice(@PathVariable String basketID, @PathVariable String productID) {
        return null;
    }
}
