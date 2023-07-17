package edu.timebandit.BasketService.port.checkout.consumer;

import edu.timebandit.BasketService.core.domain.service.interfaces.IBasketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmptyOutBasketConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmptyOutBasketConsumer.class);

    @Autowired
    private IBasketService basketService;

    @RabbitListener(queues = "empty_out_basket_queue")
    public void receiveEmptyOutBasketMessage(String basketID) {
        LOGGER.info("Received message to empty out basket: {}", basketID);

        basketService.clearBasket(basketID);
    }
}
