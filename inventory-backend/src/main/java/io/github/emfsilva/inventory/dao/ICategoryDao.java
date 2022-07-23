package io.github.emfsilva.inventory.dao;

import io.github.emfsilva.inventory.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICategoryDao extends JpaRepository<Category, Category> {
    Optional<Category> findById(Long id);
}
