package edu.timebandit.BasketService.port.checkout.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDataDTO {
    @NotNull
    private AddressDTO shippingAddress;
    @NotNull
    private AddressDTO billingAddress;
}
