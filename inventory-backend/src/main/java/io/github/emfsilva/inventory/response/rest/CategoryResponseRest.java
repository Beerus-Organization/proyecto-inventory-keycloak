package io.github.emfsilva.inventory.response.rest;

import io.github.emfsilva.inventory.response.CategoryResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponseRest extends ResponseRest {

    private CategoryResponse categoryResponse = new CategoryResponse();
}
