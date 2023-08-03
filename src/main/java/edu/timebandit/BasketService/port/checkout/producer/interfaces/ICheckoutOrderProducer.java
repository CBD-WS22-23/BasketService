package edu.timebandit.BasketService.port.checkout.producer.interfaces;

import edu.timebandit.BasketService.port.checkout.dtos.OrderDTO;
import jakarta.validation.Valid;

public interface ICheckoutOrderProducer {

            void sendCheckoutOrderMessage(@Valid OrderDTO orderDTO);
}
