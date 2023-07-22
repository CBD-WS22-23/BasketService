package edu.timebandit.BasketService.port.checkout.producer.impl;

import edu.timebandit.BasketService.port.checkout.dtos.OrderDTO;
import edu.timebandit.BasketService.port.checkout.producer.interfaces.ICheckoutOrderProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CheckoutOrderProducer implements ICheckoutOrderProducer {

    @Value("checkout_exchange")
    private String exchange;

    @Value("checkout_routing_key")
    private String checkoutRoutingKey;


    private final static Logger logger = LoggerFactory.getLogger(CheckoutOrderProducer.class);

    private final RabbitTemplate rabbitTemplate;

    public CheckoutOrderProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendCheckoutOrderMessage(OrderDTO orderDTO) {
        logger.info("Sending message to checkout basket: {}", orderDTO);
        rabbitTemplate.convertAndSend(exchange, checkoutRoutingKey, orderDTO);
    }
}
