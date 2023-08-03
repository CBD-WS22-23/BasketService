package edu.timebandit.BasketService.port.product.producer.impl;

import edu.timebandit.BasketService.port.product.producer.interfaces.IProductAddedToBasketProducer;
import edu.timebandit.BasketService.port.user.producer.impl.ProductRemovedFromBasketProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProductAddedToBasketProducer implements IProductAddedToBasketProducer {

    @Value("basket_exchange")
    private String exchange;

    @Value("product_added_to_basket_routing_key")
    private String productAddedToBasketRoutingKey;

    private static final Logger logger = LoggerFactory.getLogger(ProductRemovedFromBasketProducer.class);

    private final RabbitTemplate rabbitTemplate;

    public ProductAddedToBasketProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendProductAddedToBasketMessage(String productId) {

        logger.info("Sending message to increase inCart counter of: {}", productId);
        rabbitTemplate.convertAndSend(exchange, productAddedToBasketRoutingKey, productId);
    }
}
