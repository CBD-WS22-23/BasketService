package edu.timebandit.BasketService.core.appservice.interfaces;

import edu.timebandit.BasketService.port.product.dtos.AddProductToBasketDTO;

public interface IAddToBasketAndIncreaseInCart {
    void addAndIncrease(AddProductToBasketDTO addProductToBasketDTO);
}
