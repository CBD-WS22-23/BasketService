package edu.timebandit.BasketService.port.user.exception;

public class BasketNotFoundException extends RuntimeException {
        public BasketNotFoundException(String id) {
            super("Basket with the ID: " + id + " could not be found.");
        }
}
