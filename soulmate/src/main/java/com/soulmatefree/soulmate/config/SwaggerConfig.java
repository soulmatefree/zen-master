package com.soulmatefree.soulmate.config;

import com.soulmatefree.soulmate.utils.SwaggerGroupAutoConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 自行修改为自己的包路径
                .apis(RequestHandlerSelectors.basePackage("com.soulmatefree.soulmate.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("soulmate接口文档")
                .description("soulmate接口文档 API 1.0 操作文档")
                //服务条款网址
                .termsOfServiceUrl("http://www.ityouknow.com/")
                .version("1.0")
                .contact(new Contact("柏树子", "http://www.ityouknow.com/", "2254087660@qq.com"))
                .build();
    }


    @Bean
    public Docket userApi() {
        return getSwaggerGroupUser().createDocket();
    }

    @Bean
    @ConfigurationProperties(prefix = "swagger.user")
    public SwaggerGroupAutoConfig getSwaggerGroupUser() {
        SwaggerGroupAutoConfig config = new SwaggerGroupAutoConfig();
        return config;
    }

    @Bean
    public Docket messageApi() {
        return getSwaggerGroupMessage().createDocket();
    }

    @Bean
    @ConfigurationProperties(prefix = "swagger.message")
    public SwaggerGroupAutoConfig getSwaggerGroupMessage() {
        SwaggerGroupAutoConfig config = new SwaggerGroupAutoConfig();
        return config;
    }
}