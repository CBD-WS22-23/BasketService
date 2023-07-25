package edu.timebandit.BasketService.port.product.consumer.impl;

import edu.timebandit.BasketService.core.appservice.interfaces.IAddToBasketAndIncreaseInCart;
import edu.timebandit.BasketService.port.product.consumer.interfaces.IProductConsumer;
import edu.timebandit.BasketService.port.product.dtos.AddProductToBasketDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductConsumer implements IProductConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductConsumer.class);

    @Autowired
    private IAddToBasketAndIncreaseInCart addToBasketAndIncreaseInCart;

    @RabbitListener(queues = "add_product_to_basket_queue")
    public void consumeProductAddedToBasketMessage(@Valid AddProductToBasketDTO addProductToBasketDTO) {
        LOGGER.info("Received message to add product to basket: {}", addProductToBasketDTO);

        addToBasketAndIncreaseInCart.addAndIncrease(addProductToBasketDTO);
    }

}
