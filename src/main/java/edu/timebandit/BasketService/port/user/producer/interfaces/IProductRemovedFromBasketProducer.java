package edu.timebandit.BasketService.port.user.producer.interfaces;

public interface IProductRemovedFromBasketProducer {

            void sendProductRemovedFromBasketMessage(String productId);
}
