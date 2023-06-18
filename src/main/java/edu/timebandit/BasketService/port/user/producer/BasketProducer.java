package edu.timebandit.BasketService.port.user.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BasketProducer {

    @Value("basket_exchange")
    private String exchange;

    @Value("product_added_to_basket_routing_key")
    private String productAddedToBasketRoutingKey;

    @Value("product_removed_from_basket_routing_key")
    private String productRemovedFromBasketRoutingKey;

    private static final Logger logger = LoggerFactory.getLogger(BasketProducer.class);

    private final RabbitTemplate rabbitTemplate;

    public BasketProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendProductAddedToBasketMessage(String productId) {

        logger.info("Sending message to add product to basket: {}", productId);
        rabbitTemplate.convertAndSend(exchange, productAddedToBasketRoutingKey, productId);
    }

    public void sendProductRemovedFromBasketMessage(String productId) {
        logger.info("Sending message to remove product from basket: {}", productId);
        rabbitTemplate.convertAndSend(exchange, productRemovedFromBasketRoutingKey, productId);
    }

}
