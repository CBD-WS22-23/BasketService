package edu.timebandit.BasketService.port.user.controller;

import edu.timebandit.BasketService.core.domain.model.Watch;
import edu.timebandit.BasketService.core.domain.model.WatchDTO;
import edu.timebandit.BasketService.core.domain.service.interfaces.IBasketProductService;
import edu.timebandit.BasketService.port.user.exception.BasketProductNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class BasketProductController {

        @Autowired
        private IBasketProductService basketProductService;

        @Operation(summary = "Add a new watch to the basket service")
        @PostMapping(path = "/basketProducts")
        @ResponseStatus(HttpStatus.CREATED)
        public String create(@RequestBody WatchDTO watch) {
            return basketProductService.createBasketProduct(watch);
        }

        @Operation(summary = "Find a watch by id")
        @GetMapping("/basketProducts/{watchID}")
        public Watch getBasketProduct(@PathVariable String watchID) {
            Watch watch = basketProductService.getBasketProductByID(watchID);
            if (watch == null) {
                throw new BasketProductNotFoundException(watchID);
            }
            return watch;
        }

        @Operation(summary = "Update a watch by id")
        @PutMapping(path = "/basketProducts/{watchID}")
        @ResponseStatus(HttpStatus.OK)
        public String update(@RequestBody WatchDTO watch, @PathVariable String watchID) {
            if (basketProductService.checkIfBasketProductExists(watchID)) {
                watch.setId(watchID);
                return basketProductService.updateBasketProduct(watch);
            }
            throw new BasketProductNotFoundException(watchID);
        }

        @Operation(summary = "Delete a watch by id")
        @DeleteMapping(path = "/basketProducts/{watchID}")
        @ResponseStatus(HttpStatus.OK)
        public String delete(@PathVariable String watchID) {
            if (basketProductService.checkIfBasketProductExists(watchID)) {
                basketProductService.deleteBasketProduct(watchID);
                return "Product with id: " + watchID + " was deleted";

            }
            throw new BasketProductNotFoundException(watchID);
        }

        @Operation(summary = "Get all watches or optionally by id(s)")
        @GetMapping("/basketProducts")
        public Iterable<Watch> getBasketProducts(@RequestParam(required = false) Iterable<String> watchIDs) {
            if (watchIDs == null) {
                return basketProductService.getAllBasketProducts();
            }
            return basketProductService.getBasketProductsByIDs(watchIDs);
        }
}
