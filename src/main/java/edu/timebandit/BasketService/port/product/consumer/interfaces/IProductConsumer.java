package edu.timebandit.BasketService.port.product.consumer.interfaces;

import edu.timebandit.BasketService.port.product.dtos.AddProductToBasketDTO;
import jakarta.validation.Valid;

public interface IProductConsumer {

    void consumeProductAddedToBasketMessage(@Valid AddProductToBasketDTO addProductToBasket);
}
