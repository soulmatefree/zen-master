package com.soulmatefree.soulmate.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


@Configuration
@MapperScan(basePackages = "com.soulmatefree.soulmate.mapper", sqlSessionTemplateRef  = "soulmateSqlSessionTemplate")
public class DataSourceConfig {

    @Primary
    @Bean(name = "master")
    @ConfigurationProperties("spring.datasource.master")
    public DataSource dataSourceOne(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "soulmateSqlSessionFactory")
    @Primary
    public SqlSessionFactory soulmateSqlSessionFactory(@Qualifier("master") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/soulmatefree/soulmate/mapper/**/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "soulmateTransactionManager")
    @Primary
    public DataSourceTransactionManager soulmateTransactionManager(@Qualifier("master") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "soulmateSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate soulmateSqlSessionTemplate(@Qualifier("soulmateSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
