package edu.timebandit.BasketService.port.user.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProductRemovedFromBasketProducer {

    @Value("basket_exchange")
    private String exchange;


    @Value("product_removed_from_basket_routing_key")
    private String productRemovedFromBasketRoutingKey;

    private static final Logger logger = LoggerFactory.getLogger(ProductRemovedFromBasketProducer.class);

    private final RabbitTemplate rabbitTemplate;

    public ProductRemovedFromBasketProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void sendProductRemovedFromBasketMessage(String productId) {
        logger.info("Sending message to decrease inCart counter of: {}", productId);
        rabbitTemplate.convertAndSend(exchange, productRemovedFromBasketRoutingKey, productId);
    }

}
