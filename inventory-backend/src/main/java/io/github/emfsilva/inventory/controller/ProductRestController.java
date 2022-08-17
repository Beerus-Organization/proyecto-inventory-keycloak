package io.github.emfsilva.inventory.controller;

import io.github.emfsilva.inventory.model.Product;
import io.github.emfsilva.inventory.response.rest.ProductResponseRest;
import io.github.emfsilva.inventory.services.IProductService;
import io.github.emfsilva.inventory.utils.Util;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/v1/product")
public class ProductRestController {

    private final IProductService productService;

    public ProductRestController(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<ProductResponseRest> save(
            @RequestParam("picture")MultipartFile picture,
            @RequestParam("name") String name,
            @RequestParam("price") BigDecimal price,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("categoryId") Long categoryID) throws IOException
    {
        Product product = new Product();
        product.setName(name);
        product.setQuantity(quantity);
        product.setPrice(price);
        product.setPicture(Util.compressZLib(picture.getBytes()));

        return productService.save(product, categoryID);
    }

    @GetMapping("/products")
    public ResponseEntity<ProductResponseRest> searchProducts() {
        return productService.search();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseRest> search(@PathVariable Long id) {
        return productService.searchById(id);
    }
}
