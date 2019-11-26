package com.soulmatefree.soulmate.utils;

/**
 * @Author: baishuzi
 * @Date: 2019/3/17
 * @Description:com.soulmatefree.soulmate.utils
 * @version: 1.0
 */
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class SwaggerGroupAutoConfig {
    private String groupName;
    private String title;
    private String description;
    private String pathFilter;

    @Autowired
    private SwaggerApiInfoAutoConfig apiInfoAutoConfig;

    public SwaggerGroupAutoConfig() {
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPathFilter() {
        return this.pathFilter;
    }

    public void setPathFilter(String pathFilter) {
        this.pathFilter = pathFilter;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Docket createDocket() {
        ApiInfo apiInfo = this.apiInfoAutoConfig.createApiInfo(this);
        Docket result = (new Docket(DocumentationType.SWAGGER_2)).groupName(this.groupName).genericModelSubstitutes(new Class[]{DeferredResult.class}).useDefaultResponseMessages(false).forCodeGeneration(false).select().paths(Predicates.or(new Predicate[]{PathSelectors.regex(this.pathFilter + "/.*")})).build().apiInfo(apiInfo);
        return result;
    }
}