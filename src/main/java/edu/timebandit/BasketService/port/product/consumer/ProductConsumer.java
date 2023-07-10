package edu.timebandit.BasketService.port.product.consumer;

import edu.timebandit.BasketService.core.domain.model.Watch;
import edu.timebandit.BasketService.port.product.controller.ProductBasketController;
import edu.timebandit.BasketService.port.product.dtos.AddProductToBasketDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ProductConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductConsumer.class);

    @Qualifier("BasketModelMapper")
    @Autowired
    private ModelMapper basketModelMapper;

    @Autowired
    private ProductBasketController productBasketController;

    @RabbitListener(queues = "add_product_to_basket_queue")
    public void receiveProductAddedToBasketMessage(AddProductToBasketDTO addProductToBasketDTO) {
        LOGGER.info("Received message to add product to basket: {}", addProductToBasketDTO);

        productBasketController.addProductToBasket(addProductToBasketDTO.getBasketId(),
                basketModelMapper.map(addProductToBasketDTO.getWatch(), Watch.class), addProductToBasketDTO.getQuantity());
    }

}
