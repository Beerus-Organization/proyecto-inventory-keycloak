package io.github.emfsilva.inventory.dao;

import io.github.emfsilva.inventory.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IProductDao extends JpaRepository<Product, Long> {

    @Query("SELECT p from Product p WHERE p.name like %?1%")
    List<Product> findByNameLike(String name);
    List<Product> findByNameContainingIgnoreCase(String name);


}
