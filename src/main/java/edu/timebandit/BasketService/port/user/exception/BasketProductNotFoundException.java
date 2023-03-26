package edu.timebandit.BasketService.port.user.exception;

public class BasketProductNotFoundException extends RuntimeException{

        public BasketProductNotFoundException(String id) {
            super("BasketProduct with the ID: " + id + " could not be found.");
        }
}
