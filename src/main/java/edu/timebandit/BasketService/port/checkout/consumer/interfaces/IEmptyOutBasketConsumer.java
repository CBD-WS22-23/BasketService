package edu.timebandit.BasketService.port.checkout.consumer.interfaces;

public interface IEmptyOutBasketConsumer {

            void receiveEmptyOutBasketMessage(String basketID);
}
