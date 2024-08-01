package ru.imakhno.productsWebApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.imakhno.productsWebApp.entity.Product;
import ru.imakhno.productsWebApp.repository.ProductRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;


    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(String title, String details) {
        return productRepository.save(new Product(null, title, details));
    }

    @Override
    public Optional<Product> findProduct(long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void updateProduct(Long id, String title, String details) {
        productRepository.save(new Product(id, title, details));
    }
}

















