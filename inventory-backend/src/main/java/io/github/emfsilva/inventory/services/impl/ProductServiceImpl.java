package io.github.emfsilva.inventory.services.impl;

import io.github.emfsilva.inventory.model.Product;
import io.github.emfsilva.inventory.response.rest.ProductResponseRest;
import io.github.emfsilva.inventory.services.IProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    @Override
    public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId) {
        ProductResponseRest response = new ProductResponseRest();
        List<Product> list = new ArrayList<>();

        return null;
    }
}
