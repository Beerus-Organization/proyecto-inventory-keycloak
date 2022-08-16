package io.github.emfsilva.inventory.services.impl;

import io.github.emfsilva.inventory.dao.ICategoryDao;
import io.github.emfsilva.inventory.model.Category;
import io.github.emfsilva.inventory.model.Product;
import io.github.emfsilva.inventory.response.rest.ProductResponseRest;
import io.github.emfsilva.inventory.response.rest.ResponseRest;
import io.github.emfsilva.inventory.services.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

    private ICategoryDao categoryDao;

    public ProductServiceImpl(ICategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId) {
        ProductResponseRest response = new ProductResponseRest();
        List<Product> list = new ArrayList<>();

        try {
            // search category to set in the product object
            Optional<Category> category = categoryDao.findById(categoryId);
            if (category.isPresent()) {
                product.setCategory(category.get());
            } else {
                response.setMetadata("Request NOK", "-1", "Category association with product");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e) {

        }
        return null;
    }
}
