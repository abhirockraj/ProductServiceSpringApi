package com.springBootMegaProject.productService.controllers;

import com.springBootMegaProject.productService.dto.ProductRequest;
import com.springBootMegaProject.productService.dto.ProductResponse;
import com.springBootMegaProject.productService.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
// This claas is fo routing url
public class ProductController {

    private final ProductService objProductService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest objProjectRequest){
        objProductService.createProduct(objProjectRequest);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        return objProductService.getAllProducts();
    }

}
