package io.github.emfsilva.inventory.dao;

import io.github.emfsilva.inventory.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryDao extends JpaRepository<Category, Category> {
}
