package com.github.mrrigby.trueinvoices.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author MrRigby
 */
@Configuration
@PropertySource("classpath:/hibernate-config.properties")
public class RepositoryConfig {

    @Bean
    public DataSource dataSource(Environment env) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("db.driver"));
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setInitialSize(env.getProperty("db.initialSize", Integer.class, 2));
        dataSource.setMaxActive(env.getProperty("db.maxActive", Integer.class, 5));
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource, Environment env) {

        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();

        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan("com.github.mrrigby.trueinvoices.entity");
        factoryBean.setHibernateProperties(hibernateProperties(env));

        return factoryBean;
    }

    private Properties hibernateProperties(Environment env) {
        return new Properties() {
            {
                put("hibernate.dialect", env.getProperty("hibernate.dialect"));
                put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
                put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));

                //put("hibernate.cache.use_second_level_cache", true);
                //put("hibernate.cache.use_query_cache", true);
                //put("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
            }
        };
    }

}