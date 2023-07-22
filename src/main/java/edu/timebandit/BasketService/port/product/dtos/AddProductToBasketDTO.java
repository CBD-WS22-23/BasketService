package edu.timebandit.BasketService.port.product.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductToBasketDTO {
    @NotBlank(message = "Basket ID cannot be blank")
    private String basketId;
    @Valid
    private WatchDTO watch;
    @Min(value = 1, message = "Quantity must be greater than 0")
    private int quantity;
}
