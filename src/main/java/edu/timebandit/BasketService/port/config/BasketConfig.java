package edu.timebandit.BasketService.port.config;

import edu.timebandit.BasketService.core.domain.model.Basket;
import edu.timebandit.BasketService.core.domain.model.Watch;
import edu.timebandit.BasketService.core.domain.service.interfaces.IBasketRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.UUID;


@Configuration
public class BasketConfig {

    @Bean
    CommandLineRunner commandLineRunner(IBasketRepository basketRepository) {
        HashMap<Watch, Integer> products = new HashMap<>();
        products.put(new Watch(UUID.fromString("58b74e3e-8e46-4419-bfc6-a8c43dfa694a"), "Rolex Submariner",
                12913.00, 25, "Thumbnail"), 1);
        Basket basket = new Basket(UUID.fromString("bdfc6420-2bda-4fbb-a445-700b49ea2cc1"), products, 12913.00);
        return args -> basketRepository.save(basket);
    }
}
