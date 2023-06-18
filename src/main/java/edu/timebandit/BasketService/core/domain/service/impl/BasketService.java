package edu.timebandit.BasketService.core.domain.service.impl;

import edu.timebandit.BasketService.core.domain.model.Basket;
import edu.timebandit.BasketService.core.domain.model.Watch;
import edu.timebandit.BasketService.core.domain.service.interfaces.IBasketRepository;
import edu.timebandit.BasketService.core.domain.service.interfaces.IBasketService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
public class BasketService implements IBasketService {

    private final IBasketRepository basketRepository;

    BasketService(IBasketRepository basketRepository){
        this.basketRepository = basketRepository;
    }

    @Override
    public String createBasket() {
        return basketRepository.save(new Basket(UUID.randomUUID(), new HashMap<>(), 0)).getId().toString();
    }

    @Override
    public Double addProductToBasket(String basketID, Watch watch, int quantity) {
        return null;
    }

    @Override
    public Double removeProductFromBasket(String basketID, String watchID) {
        return null;
    }

    @Override
    public Double updateProductQuantityInBasket(String basketID, String watchID, int quantity) {
        return null;
    }

    @Override
    public void clearBasket(String basketID) {
        Basket retrievedBasket = basketRepository.findById(UUID.fromString(basketID)).orElse(null);
        if (retrievedBasket != null) {
            retrievedBasket.getProducts().clear();
            retrievedBasket.setTotalPrice(0.0);
        }
    }

    @Override
    public void deleteBasket(String basketID) {
        basketRepository.deleteById(UUID.fromString(basketID));
    }

    @Override
    public Basket getBasketByID(String basketID) {
        return basketRepository.findById(UUID.fromString(basketID)).orElse(null);
    }

    @Override
    public Iterable<Basket> getAllBaskets() {
        return basketRepository.findAll();
    }

    @Override
    public Double getBasketTotalPrice(String basketID) {
        Basket retrievedBasket = basketRepository.findById(UUID.fromString(basketID)).orElse(null);
        if (retrievedBasket != null) {
            return retrievedBasket.getTotalPrice();
        }
        return null;
    }

    @Override
    public Double getProductTotalPrice(String basketID, String watchID) {
        return null;
    }

    @Override
    public boolean checkBasketExists(String basketID) {
        return basketRepository.existsById(UUID.fromString(basketID));
    }

    private Double calculateBasketTotalPrice(Basket basket) {
        return null;
    }
}
