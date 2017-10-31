package com.jimmmy.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "projectUrl")
public class ProjectUrlConfig {

    // 微信公众账号授权url
    public String wechatMpAuthorize;

    // 微信开放平台授权url
    public String wechatOpenAuthorize;

    // 点餐系统
    public String sell;
}
