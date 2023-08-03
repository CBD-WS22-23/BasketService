package edu.timebandit.BasketService.core.appservice.impl;

import edu.timebandit.BasketService.core.appservice.interfaces.IAddToBasketAndIncreaseInCart;
import edu.timebandit.BasketService.core.domain.model.Watch;
import edu.timebandit.BasketService.core.domain.service.interfaces.IBasketService;
import edu.timebandit.BasketService.port.product.dtos.AddProductToBasketDTO;
import edu.timebandit.BasketService.port.product.producer.interfaces.IProductAddedToBasketProducer;
import edu.timebandit.BasketService.port.user.exception.BasketNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AddToBasketAndIncreaseInCart implements IAddToBasketAndIncreaseInCart {

    @Autowired
    private IBasketService basketService;

    @Autowired
    IProductAddedToBasketProducer productAddedToBasketProducer;

    @Qualifier("BasketModelMapper")
    @Autowired
    private ModelMapper basketModelMapper;

    public void addAndIncrease(AddProductToBasketDTO addProductToBasketDTO) {
        if (!basketService.checkBasketExists(addProductToBasketDTO.getBasketId())) {
            throw new BasketNotFoundException(addProductToBasketDTO.getBasketId());
        }

        int productQuantityInBasket = basketService.getProductQuantity(addProductToBasketDTO.getBasketId(),
                addProductToBasketDTO.getWatch().getId());
        if (productQuantityInBasket > 0) {
            basketService.updateProductQuantity(addProductToBasketDTO.getBasketId(),
                    addProductToBasketDTO.getWatch().getId(), addProductToBasketDTO.getQuantity()+productQuantityInBasket);
        } else {
            Watch product = basketModelMapper.map(addProductToBasketDTO.getWatch(), Watch.class);
            basketService.addProductToBasket(addProductToBasketDTO.getBasketId(), product,
                    addProductToBasketDTO.getQuantity());
            productAddedToBasketProducer.sendProductAddedToBasketMessage(product.getId().toString());
        }
    }
}
