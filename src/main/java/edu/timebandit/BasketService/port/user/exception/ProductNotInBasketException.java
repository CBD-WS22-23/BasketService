package edu.timebandit.BasketService.port.user.exception;

public class ProductNotInBasketException extends RuntimeException {
    public ProductNotInBasketException(String basketID, String watchID) {
        super("Product with id: " + watchID + " is not in basket with id: " + basketID);
    }
}
