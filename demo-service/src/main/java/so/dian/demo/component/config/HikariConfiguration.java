/*
 * Dian.so Inc.
 * Copyright (c) 2016-2019 All Rights Reserved.
 */
package so.dian.demo.component.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
//import org.jasypt.encryption.StringEncryptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

/**
 * TODO
 *
 * @author baizhang
 * @version: HikariConfiguration.java, v 1.0 2019-11-02 1:28 下午 Exp $
 */
@Slf4j
@Configuration
@ConditionalOnClass(HikariDataSource.class)
@ConditionalOnMissingBean(DataSource.class)
public class HikariConfiguration implements TransactionManagementConfigurer {
    private final HikariDataSourceProperties hikariDataSourceProperties;

    public HikariConfiguration(ObjectProvider<HikariDataSourceProperties> hikariDataSourcePropertiesProvider) {
        this.hikariDataSourceProperties = hikariDataSourcePropertiesProvider.getIfUnique();
    }

    @Bean(name = "primaryDataSource")
    @Primary
    public HikariDataSource dataSource() {
        log.info("Hikari pool。。。。");
        return createDataSource(hikariDataSourceProperties);
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("primaryDataSource") DataSource dataSource) {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //实体扫描
        bean.setTypeAliasesPackage("so.dian.demo.dao.model");

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            //mapper映射文件加载
            bean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
            bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
            return bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(this.dataSource());
    }

    protected HikariDataSource createDataSource(HikariDataSourceProperties properties) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(properties.getDriverClassName());
        hikariConfig.setJdbcUrl(properties.getUrl());
        hikariConfig.setUsername(properties.getUserName());
        hikariConfig.setPassword(properties.getPassword());
        hikariConfig.setMinimumIdle(properties.getMinimumIdle());
        hikariConfig.setMaximumPoolSize(properties.getMaximumPoolSize());
        hikariConfig.setIdleTimeout(properties.getIdleTimeout());
        hikariConfig.setAutoCommit(properties.getAutoCommit());
        hikariConfig.setMaxLifetime(properties.getMaxLifetime());
        hikariConfig.setConnectionTimeout(properties.getConnectionTimeout());
        hikariConfig.setConnectionTestQuery(properties.getConnectionTestQuery());
        hikariConfig.setPoolName(properties.getPoolName());
        return new HikariDataSource(hikariConfig);
    }


}