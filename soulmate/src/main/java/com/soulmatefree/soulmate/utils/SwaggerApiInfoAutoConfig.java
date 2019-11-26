package com.soulmatefree.soulmate.utils;

/**
 * @Author: baishuzi
 * @Date: 2019/3/17
 * @Description:com.soulmatefree.soulmate.utils
 * @version: 1.0
 */
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
@Component
public class SwaggerApiInfoAutoConfig {
    private String appContext;
    private String title;
    private String description;
    private String version;
    private String contact;
    private Boolean enable;
    private String license;
    private String licenseUrl;

    public SwaggerApiInfoAutoConfig() {
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

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getContact() {
        return this.contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLicenseUrl() {
        return this.licenseUrl;
    }

    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    public Boolean getEnable() {
        return this.enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getLicense() {
        return this.license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getAppContext() {
        return this.appContext;
    }

    public void setAppContext(String appContext) {
        this.appContext = appContext;
    }

    public ApiInfo createApiInfo() {
        return (new ApiInfoBuilder()).title(this.title).description(this.description).version(this.version).termsOfServiceUrl("NO terms of service").contact(this.contact).build();
    }

    public ApiInfo createApiInfo(SwaggerGroupAutoConfig groupConfig) {
        return (new ApiInfoBuilder()).title(groupConfig.getTitle()).description(groupConfig.getDescription()).version(this.version).termsOfServiceUrl("NO terms of service").contact(this.contact).build();
    }
}
