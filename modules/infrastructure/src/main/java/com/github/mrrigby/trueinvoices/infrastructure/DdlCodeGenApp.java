package com.github.mrrigby.trueinvoices.infrastructure;

import com.github.mrrigby.trueinvoices.infrastructure.config.RepositoryConfig;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

/**
 * @author MrRigby
 */
public class DdlCodeGenApp {

    public static void main(String[] args) {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(RepositoryConfig.class);
        LocalSessionFactoryBean localSessionFactoryBean = ctx.getBean(LocalSessionFactoryBean.class);

        Configuration configuration = localSessionFactoryBean.getConfiguration();

        SchemaUpdate schemaUpdate = new SchemaUpdate(configuration);
        schemaUpdate.setOutputFile("V001_trueinvoices_schema.sql");
        schemaUpdate.execute(true, false);
    }

}
