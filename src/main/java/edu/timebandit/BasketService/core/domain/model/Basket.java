package edu.timebandit.BasketService.core.domain.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Builder
public class Basket {
    @Id
    @Column(nullable = false, unique = true, columnDefinition = "uuid")
    @GeneratedValue (strategy = GenerationType.UUID)
    @UuidGenerator
    private UUID id;

    @ElementCollection
    private Map<String, Integer> products;

    private double totalPrice;
}
