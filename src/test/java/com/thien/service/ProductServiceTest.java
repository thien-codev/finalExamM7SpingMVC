/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thien.service;

import com.thien.entity.Products;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
////@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/jpa-config.xml"})

public class ProductServiceTest {

    @Autowired
    ProductServiceIF proService;

    @Test
    public void validateNullProductNameSupport() {
        int numOfColumnBeforeSaveProduct;
        int numOfColumnAfterSaveProduct;

        Products newProduct = new Products();
        newProduct.setUnitPrice(20.5);
        numOfColumnBeforeSaveProduct = proService.getListProducts().size();
        proService.saveProduct(newProduct);
        numOfColumnAfterSaveProduct = proService.getListProducts().size();

        Assert.assertEquals(numOfColumnAfterSaveProduct, numOfColumnBeforeSaveProduct);
    }

    @Test
    public void validateNotNullProductNameSupport() {
        int numOfColumnBeforeSaveProduct;
        int numOfColumnAfterSaveProduct;

        Products newProduct = new Products();
        newProduct.setUnitPrice(20.5);
        newProduct.setProductName("Pizza");
        numOfColumnBeforeSaveProduct = proService.getListProducts().size();
        proService.saveProduct(newProduct);
        numOfColumnAfterSaveProduct = proService.getListProducts().size();

        Assert.assertEquals(numOfColumnBeforeSaveProduct + 1, numOfColumnAfterSaveProduct);
    }
}
