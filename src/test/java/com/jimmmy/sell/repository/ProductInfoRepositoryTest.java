package com.jimmmy.sell.repository;

import com.jimmmy.sell.domain.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {
    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Test
    public void saveTest() {
        ProductInfo pi = new ProductInfo();
        pi.setProductId("123456");
        pi.setProductName("皮蛋粥");
        pi.setProductPrice(new BigDecimal(3.2));
        pi.setProductStock(100);
        pi.setProductDescription("很好喝的粥");
        pi.setProductIcon("http://xxx.jpg");
        pi.setProductStatus(0);
        pi.setCategoryType(2);
        System.out.println(pi);
        pi = productInfoRepository.save(pi);

        Assert.assertNotNull(pi);
    }

    @Test
    public void findByProductStatus() throws Exception {
        List<ProductInfo> result = productInfoRepository.findByProductStatus(0);
        Assert.assertNotEquals(0, result.size());
    }

}