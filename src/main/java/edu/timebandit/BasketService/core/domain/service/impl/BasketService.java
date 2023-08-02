package edu.timebandit.BasketService.core.domain.service.impl;

import edu.timebandit.BasketService.core.domain.model.Basket;
import edu.timebandit.BasketService.core.domain.model.Watch;
import edu.timebandit.BasketService.core.domain.service.interfaces.IBasketRepository;
import edu.timebandit.BasketService.core.domain.service.interfaces.IBasketService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Service
public class BasketService implements IBasketService {

    private final IBasketRepository basketRepository;

    BasketService(IBasketRepository basketRepository){
        this.basketRepository = basketRepository;
    }

    @Override
    public String createBasket() {
        return basketRepository.save(new Basket(UUID.randomUUID(), new HashMap<>(), 0.0)).getId().toString();
    }

    @Override
    @Transactional
    public Double addProductToBasket(String basketID, Watch watch, int quantity) {
        Optional<Basket> optionalBasket = basketRepository.findById(UUID.fromString(basketID));
        if (optionalBasket.isPresent()) {
            Basket basket = optionalBasket.get();
            int productStock = watch.getStock();
            boolean productInBasket = basket.getProducts().containsKey(watch);

            if (productInBasket) {
                int currentProductAmount = basket.getProducts().get(watch);
                basket.getProducts().put(watch, Math.min(currentProductAmount + quantity, productStock));
            } else {
                basket.getProducts().put(watch, Math.min(quantity, productStock));
            }

            double totalPrice = calculateBasketTotalPrice(basket);
            basket.setTotalPrice(totalPrice);
            basketRepository.save(basket);
            return totalPrice;
        }
        return null;
    }

    @Override
    @Transactional
    public Double removeProductFromBasket(String basketID, String watchID) {
        Optional<Basket> optionalBasket = basketRepository.findById(UUID.fromString(basketID));
        if (optionalBasket.isPresent()) {
            Basket basket = optionalBasket.get();
            for (Watch watch : basket.getProducts().keySet()) {
                if (watch.getId().toString().equals(watchID)) {
                    basket.getProducts().remove(watch);
                    break;
                }
            }
            double totalPrice = calculateBasketTotalPrice(basket);
            basket.setTotalPrice(totalPrice);
            basketRepository.save(basket);
            return totalPrice;
        }
        return null;
    }

    @Override
    @Transactional
    public Double updateProductQuantity(String basketID, String watchID, int quantity) {
        Optional<Basket> optionalBasket = basketRepository.findById(UUID.fromString(basketID));
        if (optionalBasket.isPresent()) {
            Basket basket = optionalBasket.get();
            for (Watch watch : basket.getProducts().keySet()) {
                if (watch.getId().toString().equals(watchID)) {
                    int productStock = watch.getStock();
                    basket.getProducts().put(watch, Math.min(quantity, productStock));
                    break;
                }
            }
            double totalPrice = calculateBasketTotalPrice(basket);
            basket.setTotalPrice(totalPrice);
            basketRepository.save(basket);
            return totalPrice;
        }
        return null;
    }

    @Override
    @Transactional
    public int getProductQuantity(String basketID, String watchID) {
        Basket retrievedBasket = basketRepository.findById(UUID.fromString(basketID)).orElse(null);
        if (retrievedBasket == null) {
            return -1;
        }
        for (Watch watch : retrievedBasket.getProducts().keySet()) {
            if (watch.getId().toString().equals(watchID)) {
                return retrievedBasket.getProducts().get(watch);
            }
        }
        return -1;
    }

    @Override
    @Transactional
    public void clearBasket(String basketID) {
        Basket retrievedBasket = basketRepository.findById(UUID.fromString(basketID)).orElse(null);
        if (retrievedBasket != null) {
            retrievedBasket.getProducts().clear();
            retrievedBasket.setTotalPrice(0.0);
            basketRepository.save(retrievedBasket);
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
        Basket retrievedBasket = basketRepository.findById(UUID.fromString(basketID)).orElse(null);
        if (retrievedBasket == null) {
            return null;
        }
        return calculateProductTotalPrice(retrievedBasket, watchID);
    }

    @Override
    public boolean checkBasketExists(String basketID) {
        return basketRepository.existsById(UUID.fromString(basketID));
    }


    private Double calculateBasketTotalPrice(Basket basket) {
        double totalPrice = 0.0;
        for (Watch watch : basket.getProducts().keySet()) {
            totalPrice += watch.getPrice() * basket.getProducts().get(watch);
        }
        return totalPrice;
    }

    private Double calculateProductTotalPrice(Basket basket, String watchID) {
        double totalPrice = -1.0;
        for (Watch watch : basket.getProducts().keySet()) {
            if (watch.getId().toString().equals(watchID)) {
                totalPrice = watch.getPrice() * basket.getProducts().get(watch);
                break;
            }
        }
        return totalPrice;
    }
}
