package edu.timebandit.BasketService.core.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class BasketWatch {

    @ManyToOne
    @JoinColumn(name = "watch_id")
    private Watch watch;
    private int quantity;


}
