package edu.timebandit.BasketService.port.user.advice;

import edu.timebandit.BasketService.port.user.exception.ProductNotInBasketException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ProductNotInBasketAdvice {

    @ResponseBody
    @ExceptionHandler(value = ProductNotInBasketException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String productNotInBasketHandler(ProductNotInBasketException ex){
        return ex.getMessage();
    }
}
