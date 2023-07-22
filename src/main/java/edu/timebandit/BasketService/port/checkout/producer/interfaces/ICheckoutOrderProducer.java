package edu.timebandit.BasketService.port.checkout.producer.interfaces;

import edu.timebandit.BasketService.port.checkout.dtos.OrderDTO;

public interface ICheckoutOrderProducer {

            void sendCheckoutOrderMessage(OrderDTO orderDTO);
}
