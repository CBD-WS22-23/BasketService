package edu.timebandit.BasketService.core.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "basket_watch")
public class BasketWatch {

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "watch_id", nullable = false)
    private Watch watch;
    private int quantity;


}
