package edu.timebandit.BasketService.port.checkout.dtos;

import edu.timebandit.BasketService.core.domain.model.Basket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutDTO {

    private Basket basket;
    private AddressDTO shippingAddress;
    private AddressDTO billingAddress;
    private String paymentMethod;

}
