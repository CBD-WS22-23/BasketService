package edu.timebandit.BasketService.port.product.consumer;

import edu.timebandit.BasketService.core.domain.model.Watch;
import edu.timebandit.BasketService.core.domain.service.interfaces.IBasketService;
import edu.timebandit.BasketService.port.product.dtos.AddProductToBasketDTO;
import edu.timebandit.BasketService.port.product.producer.ProductAddedToBasketProducer;
import edu.timebandit.BasketService.port.user.exception.BasketNotFoundException;
import edu.timebandit.BasketService.port.user.exception.InvalidQuantityException;
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
    private IBasketService basketService;

    @Autowired
    private ProductAddedToBasketProducer productAddedToBasketProducer;

    @RabbitListener(queues = "add_product_to_basket_queue")
    public void receiveProductAddedToBasketMessage(AddProductToBasketDTO addProductToBasketDTO) {
        LOGGER.info("Received message to add product to basket: {}", addProductToBasketDTO);

        if (addProductToBasketDTO.getQuantity() <= 0) {
            throw new InvalidQuantityException();
        }

        Watch product = basketModelMapper.map(addProductToBasketDTO.getWatch(), Watch.class);

        Double totalPrice = basketService.addProductToBasket(addProductToBasketDTO.getBasketId(), product, addProductToBasketDTO.getQuantity());
        if (totalPrice == null) {
            throw new BasketNotFoundException(addProductToBasketDTO.getBasketId());
        }
        productAddedToBasketProducer.sendProductAddedToBasketMessage(product.getId().toString());
    }

}
