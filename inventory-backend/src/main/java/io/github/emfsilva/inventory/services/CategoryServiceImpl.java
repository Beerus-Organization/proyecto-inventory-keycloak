package io.github.emfsilva.inventory.services;

import io.github.emfsilva.inventory.response.CategoryResponseRest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Override
    public ResponseEntity<CategoryResponseRest> search() {
        return null;
    }
}
