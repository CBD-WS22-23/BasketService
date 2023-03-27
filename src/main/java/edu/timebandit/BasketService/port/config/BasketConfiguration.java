package edu.timebandit.BasketService.port.config;

import edu.timebandit.BasketService.core.domain.model.Basket;
import edu.timebandit.BasketService.core.domain.model.Watch;
import edu.timebandit.BasketService.core.domain.service.interfaces.IBasketProductRepository;
import edu.timebandit.BasketService.core.domain.service.interfaces.IBasketRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;


@Configuration
public class BasketConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(IBasketRepository basketRepository, IBasketProductRepository basketProductRepository) {
        List<Watch> watches = List.of(
                new Watch(UUID.fromString("58b74e3e-8e46-4419-bfc6-a8c43dfa694a"), "Rolex Submariner",
                        null, 12913.00, 25),
                new Watch(UUID.fromString("3aba5511-18fd-4ba2-bb2c-ac90d6963dcc"), "Rolex GMT Master II",
                        null, 15889.00, 6),
                new Watch(UUID.fromString("da42d764-bcaf-48e6-a87f-8964354a9db0"), "Rolex Daytona",
                        null, 14800.00, 2),
                new Watch(UUID.fromString("a697b980-9a6e-4964-a76a-9f0824d008ce"), "Rolex Explorer",
                        null, 8499.00, 3),
                new Watch(UUID.fromString("456ee8f1-ddce-4791-8aa1-d671438bce33"), "Rolex Yacht-Master",
                        null, 9885.00, 7),
                new Watch(UUID.fromString("5bcaf4b4-29b7-483d-abea-e813f66ee63e"), "Rolex Sea-Dweller",
                        null, 14150.00, 14),
                new Watch(UUID.fromString("9febab40-4a2f-4e80-94bb-57477a045bef"), "Rolex Datejust",
                        null, 7280.00, 0),
                new Watch(UUID.fromString("1f15e18b-b74c-458e-8eb2-e074640ac181"), "Rolex Milgauss",
                        null, 10600.00, 35),
                new Watch(UUID.fromString("7a0de738-cfde-40ea-ba45-34bd4a057398"), "Rolex Sky-Dweller",
                        null, 49936.00, 1),
                new Watch(UUID.fromString("82412ae4-70a7-4e91-b9bf-cadbe4ef55f5"), "Rolex Cellini",
                        null, 3722.00, 9)

        );

        List<Basket> baskets = List.of(
                new Basket(UUID.fromString("bdfc6420-2bda-4fbb-a445-700b49ea2cc1"), new HashMap<>(), 0)
        );
        return args -> {
            basketRepository.saveAll(baskets);
            basketProductRepository.saveAll(watches);
        };
    }
}
