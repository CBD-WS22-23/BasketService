package edu.timebandit.BasketService.port.checkout.dtos;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDataDTO {
    @Valid
    private AddressDTO shippingAddress;
    @Valid
    private AddressDTO billingAddress;
}
