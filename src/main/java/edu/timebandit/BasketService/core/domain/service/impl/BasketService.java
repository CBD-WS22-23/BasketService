package edu.timebandit.BasketService.core.domain.service.impl;

import edu.timebandit.BasketService.core.domain.model.Basket;
import edu.timebandit.BasketService.core.domain.model.Watch;
import edu.timebandit.BasketService.core.domain.service.interfaces.IBasketRepository;
import edu.timebandit.BasketService.core.domain.service.interfaces.IBasketService;
import org.springframework.stereotype.Service;

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
        return basketRepository.save(new Basket(UUID.randomUUID(), new HashMap<>(), new HashMap<>(), 0.0)).getId().toString();
    }

    @Override
    public Double addProductToBasket(String basketID, Watch watch, int quantity) {
        Optional<Basket> optionalBasket = basketRepository.findById(UUID.fromString(basketID));
        if (optionalBasket.isPresent()) {
            Basket basket = optionalBasket.get();
            double productPrice = watch.getPrice();
            int productStock = watch.getStock();
            String productID = watch.getId().toString();
            boolean productInBasket = basket.getProductAmounts().containsKey(productID);

            if (productInBasket) {
                int currentProductAmount = basket.getProductAmounts().get(productID);
                if (currentProductAmount + quantity <= productStock) {
                    basket.getProductAmounts().put(productID, currentProductAmount + quantity);
                    basket.getProductPrices().put(productID, productPrice * (currentProductAmount + quantity));
                } else {
                    basket.getProductAmounts().put(productID, productStock);
                    basket.getProductPrices().put(productID, productPrice * productStock);
                }
            } else {
                if (quantity <= productStock) {
                    basket.getProductAmounts().put(productID, quantity);
                    basket.getProductPrices().put(productID, productPrice * quantity);
                } else {
                    basket.getProductAmounts().put(productID, productStock);
                    basket.getProductPrices().put(productID, productPrice * productStock);
                }
            }
            double totalPrice = calculateBasketTotalPrice(basket);
            basket.setTotalPrice(totalPrice);
            return totalPrice;
        }
        return null;
    }

    @Override
    public Double removeProductFromBasket(String basketID, String watchID) {
        Optional<Basket> optionalBasket = basketRepository.findById(UUID.fromString(basketID));
        if (optionalBasket.isPresent()) {
            Basket basket = optionalBasket.get();
            basket.getProductAmounts().remove(watchID);
            basket.getProductPrices().remove(watchID);
            double totalPrice = calculateBasketTotalPrice(basket);
            basket.setTotalPrice(totalPrice);
            return totalPrice;
        }
        return null;
    }

    @Override
    public Double updateProductQuantity(String basketID, Watch watch, int quantity) {
        Optional<Basket> optionalBasket = basketRepository.findById(UUID.fromString(basketID));
        if (optionalBasket.isPresent()) {
            Basket basket = optionalBasket.get();
            double productPrice = watch.getPrice();
            int productStock = watch.getStock();
            String productID = watch.getId().toString();
            boolean productInBasket = basket.getProductAmounts().containsKey(productID);

            if (!productInBasket) {
                return -1.0;
            }
            if (quantity <= productStock) {
                basket.getProductAmounts().put(productID, quantity);
                basket.getProductPrices().put(productID, productPrice * quantity);
            } else {
                basket.getProductAmounts().put(productID, productStock);
                basket.getProductPrices().put(productID, productPrice * productStock);
            }
            double totalPrice = calculateBasketTotalPrice(basket);
            basket.setTotalPrice(totalPrice);
            return totalPrice;
        }
        return null;
    }

    @Override
    public void clearBasket(String basketID) {
        Basket retrievedBasket = basketRepository.findById(UUID.fromString(basketID)).orElse(null);
        if (retrievedBasket != null) {
            retrievedBasket.getProductAmounts().clear();
            retrievedBasket.getProductPrices().clear();
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
        Basket retrievedBasket = basketRepository.findById(UUID.fromString(basketID)).orElse(null);
        if (retrievedBasket == null) {
            return null;
        }
        return retrievedBasket.getProductPrices().getOrDefault(watchID, -1.0);
    }

    @Override
    public boolean checkBasketExists(String basketID) {
        return basketRepository.existsById(UUID.fromString(basketID));
    }

    private Double calculateBasketTotalPrice(Basket basket) {
        return basket.getProductPrices().values().stream().mapToDouble(Double::doubleValue).sum();
    }
}
