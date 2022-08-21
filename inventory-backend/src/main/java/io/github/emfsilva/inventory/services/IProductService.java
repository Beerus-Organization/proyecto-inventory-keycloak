package io.github.emfsilva.inventory.services;

import io.github.emfsilva.inventory.model.Product;
import io.github.emfsilva.inventory.response.rest.ProductResponseRest;
import org.springframework.http.ResponseEntity;

public interface IProductService {

    ResponseEntity<ProductResponseRest> save(Product product, Long categoryId);

    ResponseEntity<ProductResponseRest> search();

    ResponseEntity<ProductResponseRest> searchById(Long id);

    ResponseEntity<ProductResponseRest> searchByName(String name);

    ResponseEntity<ProductResponseRest> deleteById(Long id);

    ResponseEntity<ProductResponseRest> update(Product product, Long categoryId, Long id);
}
