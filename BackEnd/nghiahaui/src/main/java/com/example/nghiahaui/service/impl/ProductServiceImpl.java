package com.example.nghiahaui.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.nghiahaui.entity.Category;
import com.example.nghiahaui.entity.Image;
import com.example.nghiahaui.entity.Product;
import com.example.nghiahaui.exception.NotFoundException;
import com.example.nghiahaui.model.request.CreateProductRequest;
import com.example.nghiahaui.repository.CategoryRepository;
import com.example.nghiahaui.repository.ImageRepository;
import com.example.nghiahaui.repository.ProductRepository;
import com.example.nghiahaui.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public List<Product> getList() {
        // TODO Auto-generated method stub
        return productRepository.findAll(Sort.by("id").descending());
    }

    @Override
    public Product getProduct(long id) {
        // TODO Auto-generated method stub
        Product product= productRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Product With Id: " + id));

        return product;
    }
    

    @Override
    public Product createProduct(CreateProductRequest request) {
        // TODO Auto-generated method stub
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(()-> new NotFoundException("Not Found Category With Id: " + request.getCategoryId()));
        product.setCategory(category);

        Set<Image> images = new HashSet<>();
        for(long imageId: request.getImageIds()){
            Image image = imageRepository.findById(imageId).orElseThrow(() -> new NotFoundException("Not Found Image With Id: " + imageId));
            images.add(image);
        }
        product.setImages(images);
        productRepository.save(product);
        return product;
    }

    @Override
    public Product updateProduct(long id, CreateProductRequest request) {
        // TODO Auto-generated method stub
        Product product= productRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Product With Id: " + id));
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(()-> new NotFoundException("Not Found Category With Id: " + request.getCategoryId()));
        product.setCategory(category);

        Set<Image> images = new HashSet<>();
        for(long imageId: request.getImageIds()){
            Image image = imageRepository.findById(imageId).orElseThrow(() -> new NotFoundException("Not Found Image With Id: " + imageId));
            images.add(image);
        }
        product.setImages(images);
        productRepository.save(product);

        return product;
    }

    @Override
    public void deleteProduct(long id) {
        // TODO Auto-generated method stub
        Product product= productRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Product With Id: " + id));
        product.getImages().remove(this);
        productRepository.delete(product);
    }

    @Override
    public List<Product> getListNewst(int number) {
        // TODO Auto-generated method stub
        List<Product> list = productRepository.getListNewest(number);
        return list;
    }

    @Override
    public List<Product> getListByPrice() {
        // TODO Auto-generated method stub
        return productRepository.getListByPrice();
    }

    @Override
    public List<Product> findRelatedProduct(long id){
        List<Product> list = productRepository.findRelatedProduct(id);
        return list;

    }

    @Override
    public List<Product> getListProductByCategory(long id){
        List<Product> list =productRepository.getListProductByCategory(id);
        return list;
    }

    @Override
    public List<Product> getListByPriceRange(long id,int min, int max){
        List<Product> list =productRepository.getListProductByPriceRange(id, min, max);
        return list;
    }

    @Override
    public List<Product> searchProduct(String keyword) {
        // TODO Auto-generated method stub
        List<Product> list = productRepository.searchProduct(keyword);
        return list;
    }
    @Override
    public void updateProductQuantity(Long id, Integer quantity) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setQuantity(quantity);
            productRepository.save(product);
        } else {
            throw new NotFoundException("Product not found with id: " + id);
        }
    }


}
