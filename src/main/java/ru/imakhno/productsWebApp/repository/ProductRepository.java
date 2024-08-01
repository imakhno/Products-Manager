package ru.imakhno.productsWebApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.imakhno.productsWebApp.entity.Product;

import java.util.*;
import java.util.stream.IntStream;

public interface ProductRepository extends JpaRepository<Product, Long> {

}