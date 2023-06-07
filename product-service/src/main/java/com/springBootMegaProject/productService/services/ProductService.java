package com.springBootMegaProject.productService.services;

import com.springBootMegaProject.productService.dto.ProductRequest;
import com.springBootMegaProject.productService.dto.ProductResponse;
import com.springBootMegaProject.productService.models.Products;
import com.springBootMegaProject.productService.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
// this class is for writing business logic which is used inside the controllers
public class ProductService {

    private final ProductRepository objProductRepository;
    public List<ProductResponse> getAllProducts(){
        //mined for db
        List<Products> products = objProductRepository.findAll();

        return  products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Products product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    public  void createProduct(ProductRequest objProductRequest){
        Products objProducts = Products.builder()
                .name(objProductRequest.getName())
                .description(objProductRequest.getDescription())
                .price(objProductRequest.getPrice())
                .build();
        objProductRepository.save(objProducts);
        log.info("Product {} is saved", objProducts.getId());
    }
}
