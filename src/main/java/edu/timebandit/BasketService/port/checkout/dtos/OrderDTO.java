package edu.timebandit.BasketService.port.checkout.dtos;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    @Valid
    private BasketDTO basket;
    @Valid
    private AddressDTO shippingAddress;
    @Valid
    private AddressDTO billingAddress;
    @NotBlank(message = "Payment method cannot be blank")
    private String paymentMethod;

}
