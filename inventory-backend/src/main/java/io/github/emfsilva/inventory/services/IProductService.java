package io.github.emfsilva.inventory.services;

import io.github.emfsilva.inventory.model.Product;
import io.github.emfsilva.inventory.response.ProductResponse;
import io.github.emfsilva.inventory.response.rest.ProductResponseRest;
import org.springframework.http.ResponseEntity;

public interface IProductService {

    ResponseEntity<ProductResponseRest> save(Product product, Long categoryId);
}
