package edu.timebandit.BasketService.core.domain.service.interfaces;

import edu.timebandit.BasketService.core.domain.model.Basket;
import edu.timebandit.BasketService.core.domain.model.Watch;

public interface IBasketService {

    String createBasket();

    Double addProductToBasket(String basketID, Watch watch, int quantity);

    Double removeProductFromBasket(String basketID, String watchID);

    Double updateProductQuantityInBasket(String basketID, String watchID, int quantity);

    String clearBasket(String basketID);

    void deleteBasket(String basketID);

    Basket getBasketByID(String basketID);

    Iterable<Basket> getAllBaskets();

    Double getBasketTotalPrice(String basketID);
}
