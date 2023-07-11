package edu.timebandit.BasketService.port.checkout.producer;

import edu.timebandit.BasketService.port.checkout.dtos.CheckoutDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CheckoutOrderProducer {

    @Value("checkout_exchange")
    private String exchange;

    @Value("checkout_routing_key")
    private String checkoutRoutingKey;


    private final static Logger logger = LoggerFactory.getLogger(CheckoutOrderProducer.class);

    private final RabbitTemplate rabbitTemplate;

    public CheckoutOrderProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendCheckoutOrderMessage(CheckoutDTO checkoutDTO) {
        logger.info("Sending message to checkout basket: {}", checkoutDTO);
        rabbitTemplate.convertAndSend(exchange, checkoutRoutingKey, checkoutDTO);
    }



}
