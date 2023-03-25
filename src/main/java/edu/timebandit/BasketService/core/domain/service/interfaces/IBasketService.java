package edu.timebandit.BasketService.core.domain.service.interfaces;

public interface IBasketService {

    String createBasket();

    String addProductToBasket(String basketID, String watchID, int quantity);

    String removeProductFromBasket(String basketID, String watchID);

    String updateProductQuantityInBasket(String basketID, String watchID, int quantity);

    String clearBasket(String basketID);

    String deleteBasket(String basketID);

    String getBasket(String basketID);

    String getAllBaskets();

    String getBasketTotalPrice(String basketID);
}
