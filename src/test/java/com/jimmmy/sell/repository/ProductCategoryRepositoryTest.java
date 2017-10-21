package com.jimmmy.sell.repository;

import com.jimmmy.sell.domain.ProductCategory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void findOneTest() {
        ProductCategory pc = productCategoryRepository.findOne(1);
        System.out.println(pc.toString());
    }

    @Test
    @Transactional
    public void saveTest() {
        ProductCategory pc = new ProductCategory("男生最爱", 3);
        ProductCategory p = productCategoryRepository.save(pc);
        Assert.assertNotEquals(null, p);
    }

    @Test
    public void findByCategoryTypeInTest() {
        List<Integer> list = Arrays.asList(1, 2);
        List<ProductCategory> result = productCategoryRepository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0, result.size());
    }
}