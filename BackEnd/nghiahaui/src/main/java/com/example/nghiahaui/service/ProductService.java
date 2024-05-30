package com.example.nghiahaui.service;

import java.util.List;
import java.util.Optional;

import com.example.nghiahaui.entity.Product;
import com.example.nghiahaui.model.request.CreateProductRequest;

public interface ProductService {
    
    List<Product> getList();

    List<Product> getListNewst(int number);

    List<Product> getListByPrice();

    List<Product> findRelatedProduct(long id);

    List<Product> getListProductByCategory(long id);

    List<Product> getListByPriceRange(long id,int min, int max);

    List<Product> searchProduct(String keyword);

    Product getProduct(long id);

    Product createProduct(CreateProductRequest request);

    Product updateProduct(long id, CreateProductRequest request);

    void deleteProduct(long id);

    void updateProductQuantity(Long id, Integer quantity);
}
