package edu.timebandit.BasketService.port.checkout.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasketDTO {

    private String id;

    private List<WatchDTO> watches;

    private double totalPrice;
}
