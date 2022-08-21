package io.github.emfsilva.inventory.services.impl;

import io.github.emfsilva.inventory.dao.ICategoryDao;
import io.github.emfsilva.inventory.dao.IProductDao;
import io.github.emfsilva.inventory.model.Category;
import io.github.emfsilva.inventory.model.Product;
import io.github.emfsilva.inventory.response.ProductResponse;
import io.github.emfsilva.inventory.response.rest.ProductResponseRest;
import io.github.emfsilva.inventory.services.IProductService;
import io.github.emfsilva.inventory.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ICategoryDao categoryDao;
    private final IProductDao productDao;

    public ProductServiceImpl(ICategoryDao categoryDao, IProductDao productDao) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
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
                response.setMetadata("Request NOK", "-1", "Category no associated with product");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            // save the product
            Product productSaved = productDao.save(product);

            if(productSaved != null) {
                list.add(productSaved);
                response.getProduct().setProducts(list);
                response.setMetadata("Request OK", "00", "Product saved");
            } else {
                response.setMetadata("Request NOK", "-1", "Product cant be save");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e) {
            response.setMetadata("Request NOK", "-1", "Error to save product");
            LOGGER.info("Error to save product {}", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductResponseRest> search() {
        ProductResponseRest response = new ProductResponseRest();
        List<Product> list = new ArrayList<>();
        List<Product> listAux = new ArrayList<>();

        try {
            listAux = productDao.findAll();

            if(listAux.size() > 0 ) {
                listAux.forEach((product -> {
                    byte[] imageDecompressed = Util.decompressZLib(product.getPicture());
                    product.setPicture(imageDecompressed);
                    list.add(product);
                }));

                response.getProduct().setProducts(list);
                response.setMetadata("Request OK", "00", "Request success");

            } else {
                response.setMetadata("Request NOK", "-1", "Product not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

        } catch (Exception e) {
            response.setMetadata("Request NOK", "-1", "Error to search");
            LOGGER.info("Error to search {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductResponseRest> searchById(Long id) {
        ProductResponseRest response = new ProductResponseRest();
        List<Product> list = new ArrayList<>();

        try {
            // search product by ID
            Optional<Product> product = productDao.findById(id);
            if(product.isPresent()) {

                byte[] imageDecompressed = Util.decompressZLib(product.get().getPicture());
                product.get().setPicture(imageDecompressed);

                list.add(product.get());
                response.getProduct().setProducts(list);
                response.setMetadata("Request OK", "00", "Request success");

            } else {
                response.setMetadata("Request NOK", "-1", "Product not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e){
            response.setMetadata("Request NOK", "-1", "Error to search by ID");
            LOGGER.info("Error to search {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductResponseRest> searchByName(String name) {
        ProductResponseRest response = new ProductResponseRest();
        List<Product> list = new ArrayList<>();
        List<Product> listAux;

        try {
            // search product by Name
            listAux = productDao.findByNameContainingIgnoreCase(name);

            if(listAux.size() > 0 ) {
                listAux.forEach((product -> {
                    byte[] imageDecompressed = Util.decompressZLib(product.getPicture());
                    product.setPicture(imageDecompressed);
                    list.add(product);
                }));

                response.getProduct().setProducts(list);
                response.setMetadata("Request OK", "00", "Request success");

            } else {
                response.setMetadata("Request NOK", "-1", "Product not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e){
            response.setMetadata("Request NOK", "-1", "Error to search by name");
            LOGGER.info("Error to search {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ProductResponseRest> deleteById(Long id) {
        ProductResponseRest response = new ProductResponseRest();

        try {

            var productSearch = productDao.findById(id);
            if(productSearch.isPresent()) {
                productDao.deleteById(id);
                response.setMetadata("Request OK", "00","Request success" );
            } else {
                response.setMetadata("Request NOK", "-1", "Category not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        }catch (Exception e) {
            response.setMetadata("Request NOK", "-1", "Error to delete product");
            LOGGER.info("Error to delete {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
