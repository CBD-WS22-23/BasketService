package edu.timebandit.BasketService.core.domain.service.interfaces;

import edu.timebandit.BasketService.core.domain.model.Basket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IBasketRepository extends CrudRepository<Basket, UUID> {
}
