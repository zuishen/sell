package com.jimmmy.sell.service.impl;

import com.jimmmy.sell.domain.ProductInfo;
import com.jimmmy.sell.enums.ProductStatusEnum;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {
    @Autowired
    private ProductServiceImpl productService;

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findOne() throws Exception {
        ProductInfo pi = productService.findOne("123456");
        Assert.assertEquals("123456", pi.getProductId());
    }

    @Test
    public void findUpAll() throws Exception {
        List<ProductInfo> result = productService.findUpAll();
        Assert.assertNotEquals(0, result.size());
    }

    @Test
    public void findAll() throws Exception {
        PageRequest request = new PageRequest(0, 2);
        Page<ProductInfo> productInfoPage = productService.findAll(request);
        // System.out.println(productInfoPage);
        Assert.assertNotEquals(0, productInfoPage.getTotalElements());
    }

    @Test
    public void save() throws Exception {
        ProductInfo pi = new ProductInfo();
        pi.setProductId("123457");
        pi.setProductName("海鲜粥");
        pi.setProductPrice(new BigDecimal(4.0));
        pi.setProductStock(100);
        pi.setProductDescription("很美味的粥");
        pi.setProductIcon("http://xxx.jpg");
        pi.setProductStatus(ProductStatusEnum.DOWN.getCode());
        pi.setCategoryType(2);
        System.out.println(pi);
        pi = productService.save(pi);

        Assert.assertNotNull(pi);
    }

}