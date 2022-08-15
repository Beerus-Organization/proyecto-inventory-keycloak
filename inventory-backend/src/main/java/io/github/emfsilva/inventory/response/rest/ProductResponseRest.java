package io.github.emfsilva.inventory.response.rest;

import io.github.emfsilva.inventory.response.ProductResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseRest extends ResponseRest {

    private ProductResponse product = new ProductResponse();
}
