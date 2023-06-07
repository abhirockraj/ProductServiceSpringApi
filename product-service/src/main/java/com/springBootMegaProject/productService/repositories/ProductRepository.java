package com.springBootMegaProject.productService.repositories;

import com.springBootMegaProject.productService.models.Products;
import org.springframework.data.mongodb.repository.MongoRepository;
// this class is fo connecting mongo db to model layer
public interface ProductRepository extends MongoRepository<Products,String > {
}
