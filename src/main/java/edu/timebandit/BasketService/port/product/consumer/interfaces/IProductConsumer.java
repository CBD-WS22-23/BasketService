package edu.timebandit.BasketService.port.product.consumer.interfaces;

import edu.timebandit.BasketService.port.product.dtos.AddProductToBasketDTO;

public interface IProductConsumer {

    void consumeProductAddedToBasketMessage(AddProductToBasketDTO addProductToBasket);
}
