package com.thien.repository;

import com.thien.entity.Products;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Products, Integer> {

    public Products findById(int id);

    @Query("select o from Products o where o.productName like %?1%")
    public List<Products> findByProductName(String name);
}
