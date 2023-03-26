package edu.timebandit.BasketService.core.domain.service.impl;

import edu.timebandit.BasketService.core.domain.model.Basket;
import edu.timebandit.BasketService.core.domain.model.BasketWatch;
import edu.timebandit.BasketService.core.domain.model.Watch;
import edu.timebandit.BasketService.core.domain.service.interfaces.IBasketRepository;
import edu.timebandit.BasketService.core.domain.service.interfaces.IBasketService;

import java.util.HashMap;
import java.util.UUID;

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
        Basket retrievedBasket = basketRepository.findById(UUID.fromString(basketID)).orElse(null);
        if (retrievedBasket != null) {
            if (retrievedBasket.getProducts().containsKey(watch.getId().toString())) {
                BasketWatch basketWatch = retrievedBasket.getProducts().get(watch.getId().toString());
                basketWatch.setQuantity(basketWatch.getQuantity() + quantity);
            } else {
                retrievedBasket.getProducts().put(watch.getId().toString(), new BasketWatch(watch, quantity));
            }
            return calculateBasketTotalPrice(retrievedBasket);
        }

        return null;
    }

    @Override
    public Double removeProductFromBasket(String basketID, String watchID) {
        Basket retrievedBasket = basketRepository.findById(UUID.fromString(basketID)).orElse(null);
        if (retrievedBasket != null) {
            retrievedBasket.getProducts().remove(watchID);
            return calculateBasketTotalPrice(retrievedBasket);
        }
        return null;
    }

    @Override
    public Double updateProductQuantityInBasket(String basketID, String watchID, int quantity) {
        Basket retrievedBasket = basketRepository.findById(UUID.fromString(basketID)).orElse(null);
        if (retrievedBasket != null) {
            if (retrievedBasket.getProducts().containsKey(watchID)) {
                BasketWatch basketWatch = retrievedBasket.getProducts().get(watchID);
                basketWatch.setQuantity(quantity);
            }
            return calculateBasketTotalPrice(retrievedBasket);
        }
        return null;
    }

    @Override
    public String clearBasket(String basketID) {
        Basket retrievedBasket = basketRepository.findById(UUID.fromString(basketID)).orElse(null);
        if (retrievedBasket != null) {
            retrievedBasket.getProducts().clear();
            retrievedBasket.setTotalPrice(0.0);
            return basketRepository.save(retrievedBasket).getId().toString();
        }
        return null;
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

    private Double calculateBasketTotalPrice(Basket basket) {
        double totalPrice = 0.0;
        for (BasketWatch basketWatch : basket.getProducts().values()) {
            totalPrice += basketWatch.getWatch().getPrice() * basketWatch.getQuantity();
        }
        basket.setTotalPrice(totalPrice);
        basketRepository.save(basket);
        return totalPrice;
    }
}
