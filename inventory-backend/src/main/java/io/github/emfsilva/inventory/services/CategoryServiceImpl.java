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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            response.setMetadata("Request OK", "00", "Request success");

        } catch (Exception e) {
            response.setMetadata("Request NOK", "-1", "Error to search");
            LOGGER.info("Error to search {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoryResponseRest> searchById(Long id) {
        CategoryResponseRest response = new CategoryResponseRest();
        List<Category> list = new ArrayList<>();

        try {
            Optional<Category> category = categoryDao.findById(id);

            if (category.isPresent()) {
                list.add(category.get());
                response.getCategoryResponse().setCategory(list);
                response.setMetadata("Request OK", "00", "Request success");
            } else {
                response.setMetadata("Request NOK", "-1", "Category not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

        } catch (Exception e) {
            response.setMetadata("Request NOK", "-1", "Error to search by ID");
            LOGGER.info("Error to search {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CategoryResponseRest> save(Category category) {
        CategoryResponseRest response = new CategoryResponseRest();
        List<Category> list = new ArrayList<>();

        try {
            Category categorySaved = categoryDao.save(category);
            list.add(categorySaved);
            response.getCategoryResponse().setCategory(list);
            response.setMetadata("Request OK", "00", "Request success");

        } catch (Exception e) {
            response.setMetadata("Request NOK", "-1", "Error to save category");
            LOGGER.info("Error to search {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
