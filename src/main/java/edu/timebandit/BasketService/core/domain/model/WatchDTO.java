package edu.timebandit.BasketService.core.domain.model;

import lombok.Data;

@Data
public class WatchDTO {
    private String id;
    private String name;
    private String thumbLink;
    private double price;
    private int stock;
}
