/*
 * Dian.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package so.dian.demo.component.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis扫描接口
 * 初始化之前，先初始化MyBatisConfiguration
 *
 * @author ${baizhang}
 * @version $Id: MapperScannerConfiguration.java, v 0.1 2017-11-27 下午7:07 Exp $
 */
@Configuration
@AutoConfigureAfter(HikariDataSourceProperties.class)
public class MyBatisMapperScannerConfiguration {
    @Bean
    public MapperScannerConfigurer mapperScannerConfiguration() {
        MapperScannerConfigurer mapper = new MapperScannerConfigurer();
        mapper.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapper.setBasePackage("so.dian.demo.dao");
        return mapper;
    }
}