package edu.timebandit.BasketService.port.product.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductToBasketDTO {
    private String basketId;
    private WatchDTO watch;
    private int quantity;
}
