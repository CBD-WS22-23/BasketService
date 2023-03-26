package edu.timebandit.BasketService.port.user.advice;

import edu.timebandit.BasketService.port.user.exception.BasketProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BasketProductNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(value = BasketProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String basketProductNotFoundHandler(BasketProductNotFoundException ex){
        return ex.getMessage();
    }
}
