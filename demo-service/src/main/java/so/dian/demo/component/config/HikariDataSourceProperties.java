/*
 * Dian.so Inc.
 * Copyright (c) 2016-2019 All Rights Reserved.
 */
package so.dian.demo.component.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * TODO
 *
 * @author baizhang
 * @version: HikariDataSourceProperties.java, v 1.0 2019-11-02 1:33 下午 Exp $
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "hikari.datasource.mysql")
public class HikariDataSourceProperties {
    private Class<? extends DataSource> type;
    private String driverClassName;
    private String url;
    private String userName;
    private String password;

    private String poolName;
    private Integer minimumIdle;
    private Integer maximumPoolSize;
    private Integer idleTimeout;
    private Boolean autoCommit;
    private Integer maxLifetime;
    private Integer connectionTimeout;
    private String connectionTestQuery;
}