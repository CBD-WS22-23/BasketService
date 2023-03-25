package edu.timebandit.BasketService.core.domain.service.interfaces;

import edu.timebandit.BasketService.core.domain.model.Watch;
import edu.timebandit.BasketService.core.domain.model.WatchDTO;

public interface IBasketProductService {

    String createBasketProduct(WatchDTO watch);

    String updateBasketProduct(WatchDTO watch, String watchID);

    void deleteBasketProduct(String watchID);

    Watch getBasketProductByID(String watchID);

    Iterable<Watch> getBasketProductsByIDs(Iterable<String> watchIDs);

    boolean checkIfBasketProductExists(String watchID);

    Iterable<Watch> getAllBasketProducts();
}
