package edu.timebandit.BasketService.port.user.consumer;

import edu.timebandit.BasketService.core.domain.model.WatchDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ProductConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductConsumer.class);

    @RabbitListener(queues = "create_product_queue")
    public void consumeCreateProductMessage(WatchDTO watch) {
        LOGGER.info("Received message to create product: {}", watch);
    }

    @RabbitListener(queues = "update_product_queue")
    public void consumeUpdateProductMessage(WatchDTO watch) {
        LOGGER.info("Received message to update product: {}", watch);
    }

    @RabbitListener(queues = "delete_product_queue")
    public void consumeDeleteProductMessage(WatchDTO watch) {
        LOGGER.info("Received message to delete product: {}", watch);
    }

}
