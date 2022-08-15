package io.github.emfsilva.inventory.response;

import io.github.emfsilva.inventory.model.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {

    List<Product> products;

}
