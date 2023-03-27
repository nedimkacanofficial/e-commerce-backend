package com.ndmkcn.ecommerce.dao;

import com.ndmkcn.ecommerce.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(collectionResourceRel = "productCategorys",path = "product-categorys")
@CrossOrigin(origins = "http://localhost:4200")
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Long> {
}
