package ru.imakhno.productsWebApp.service;

import ru.imakhno.productsWebApp.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAllProducts();
    Product createProduct(String title, String details);
    Optional<Product> findProduct(long productId);

    void deleteProduct(Long id);

    void updateProduct(Long id, String title, String details);
}
