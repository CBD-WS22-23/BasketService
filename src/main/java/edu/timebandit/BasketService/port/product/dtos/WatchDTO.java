package edu.timebandit.BasketService.port.product.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WatchDTO {
    @NotBlank(message = "ID cannot be blank")
    private String id;
    private String name;
    @Min(value = 0, message = "Price must be greater than 0")
    private double price;
    @Min(value = 1, message = "Stock must be greater than 0")
    private int stock;
    private String thumbnail;
}
