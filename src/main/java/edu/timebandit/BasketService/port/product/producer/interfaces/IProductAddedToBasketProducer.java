package edu.timebandit.BasketService.port.product.producer.interfaces;

public interface IProductAddedToBasketProducer {

        void sendProductAddedToBasketMessage(String productId);
}
