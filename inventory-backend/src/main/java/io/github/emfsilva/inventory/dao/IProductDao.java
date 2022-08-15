package io.github.emfsilva.inventory.dao;

import io.github.emfsilva.inventory.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductDao extends JpaRepository<Product, Long> {
}
