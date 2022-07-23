package io.github.emfsilva.inventory.services;

import io.github.emfsilva.inventory.dao.ICategoryDao;
import io.github.emfsilva.inventory.model.Category;
import io.github.emfsilva.inventory.response.CategoryResponseRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

   private final ICategoryDao categoryDao;
    @Autowired
    public CategoryServiceImpl(ICategoryDao iCategoryDao) {
        this.categoryDao = iCategoryDao;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoryResponseRest> search() {
        CategoryResponseRest response = new CategoryResponseRest();

        try {
            List<Category> categories = categoryDao.findAll();
            response.getCategoryResponse().setCategory(categories);
            response.setMetadata("Request OK","00","Request success");

        }catch (Exception e) {
            response.setMetadata("Request NOK","-1","Error to search");
            LOGGER.info("Error to search {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
