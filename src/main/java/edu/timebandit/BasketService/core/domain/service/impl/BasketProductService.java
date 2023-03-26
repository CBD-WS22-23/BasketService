package edu.timebandit.BasketService.core.domain.service.impl;

import edu.timebandit.BasketService.core.domain.model.Watch;
import edu.timebandit.BasketService.core.domain.model.WatchDTO;
import edu.timebandit.BasketService.core.domain.service.interfaces.IBasketProductRepository;
import edu.timebandit.BasketService.core.domain.service.interfaces.IBasketProductService;

import java.util.HashSet;
import java.util.UUID;

public class BasketProductService implements IBasketProductService {

    private final IBasketProductRepository basketProductRepository;

    BasketProductService(IBasketProductRepository basketProductRepository){
        this.basketProductRepository = basketProductRepository;
    }

    @Override
    public String createBasketProduct(WatchDTO watch) {
        return basketProductRepository.save(new Watch(UUID.fromString(watch.getId()), watch.getName(),
                watch.getThumbLink(),
                watch.getPrice(), watch.getStock())).getId().toString();
    }

    @Override
    public String updateBasketProduct(WatchDTO watch) {
        return basketProductRepository.save(new Watch(UUID.fromString(watch.getId()), watch.getName(),
                watch.getThumbLink(),
                watch.getPrice(), watch.getStock())).getId().toString();
    }

    @Override
    public void deleteBasketProduct(String watchID) {
        basketProductRepository.deleteById(UUID.fromString(watchID));
    }

    @Override
    public Watch getBasketProductByID(String watchID) {
        return basketProductRepository.findById(UUID.fromString(watchID)).orElse(null);
    }

    @Override
    public Iterable<Watch> getBasketProductsByIDs(Iterable<String> watchIDs) {
        HashSet<UUID> watchUUIDs = new HashSet<>();
        for (String watchID : watchIDs) {
            watchUUIDs.add(UUID.fromString(watchID));
        }
        return basketProductRepository.findAllById(watchUUIDs);
    }

    @Override
    public boolean checkIfBasketProductExists(String watchID) {
        return basketProductRepository.existsById(UUID.fromString(watchID));
    }

    @Override
    public Iterable<Watch> getAllBasketProducts() {
        return basketProductRepository.findAll();
    }
}
