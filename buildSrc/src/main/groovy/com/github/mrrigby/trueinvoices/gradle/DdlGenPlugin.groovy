package com.github.mrrigby.trueinvoices.gradle
import org.apache.commons.dbcp2.BasicDataSource
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.FileCollection
import org.hibernate.cfg.Configuration
import org.hibernate.tool.hbm2ddl.SchemaUpdate
import org.reflections.Reflections
import org.springframework.data.jdbc.support.DatabaseType

import javax.persistence.Entity
/**
 * @author MrRigby
 */
class DdlGenPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.extensions.create("exDdlGen", DdlCodeGenPluginExtension)

        project.task('ddlGen') << {
            println "Generating SQL schema..."

            enrichClasspath(project.exDdlGen)

            def migrationFilePath = "${project.exDdlGen.migrationPath}/V001__schema.sql"
            def hibernateCfg = createHibernateConfig(project.exDdlGen)
            exportSqlSchema(hibernateCfg, migrationFilePath)


            println "Schema generation completed successfully ..."
        }
    }

    private void enrichClasspath(exDdlGen) {
        def classLoader = Thread.currentThread().getContextClassLoader()
        exDdlGen.classpath.each { file ->
            classLoader.addURL(file.toURI().toURL())
        }
    }

    private def createHibernateConfig(exDdlGen) {
        Configuration hibernateCfg = new Configuration()

        // add all annotated entities from package: exDdlGen.packageName
        println "scanning the package ${exDdlGen.packageName} for entities..."
        def reflections = new Reflections(exDdlGen.packageName)
        def Set<Class<?>> entities = reflections.getTypesAnnotatedWith(Entity.class)
        for (Class<?> eachEntity: entities) {
            println "added entity for processing: ${eachEntity}"
            hibernateCfg.addAnnotatedClass(eachEntity)
        }

        // some autodetection before we proceed with filling in the properties
        def dialect = detectDialect(exDdlGen)
        println "Detected dialect: ${dialect}"

        // and other required properties
        Properties myProperties = new Properties();
        myProperties.put("hibernate.connection.driver_class", exDdlGen.driverClassName)
        myProperties.put("hibernate.connection.url",exDdlGen.url)
        myProperties.put("hibernate.connection.username", exDdlGen.user)
        myProperties.put("hibernate.connection.password", exDdlGen.password)
        myProperties.put("hibernate.dialect", dialect)
        myProperties.put("hibernate.schema_update.unique_constraint_strategy", "recreate_quietly")
        println "all the properties we use with Hibernate Configuration: ${myProperties}"

        hibernateCfg.addProperties(myProperties)
        hibernateCfg
    }

    private def String detectDialect(exDdlGen) {

        // let's create a datasource
        def dataSource = new BasicDataSource()
        dataSource.setDriverClassName(exDdlGen.driverClassName)
        dataSource.setUrl(exDdlGen.url)
        dataSource.setUsername(exDdlGen.user)
        dataSource.setPassword(exDdlGen.password)

        // then detect dialect from DataSource's MetaData
        String dialect
        DatabaseType dbType = DatabaseType.fromMetaData(dataSource)
        if (DatabaseType.H2 == dbType) {
            dialect = "org.hibernate.dialect.H2Dialect"
        } else if (DatabaseType.DERBY == dbType) {
            dialect = "org.hibernate.dialect.DerbyTenSevenDialect"
        } else if (DatabaseType.POSTGRES == dbType) {
            dialect = "org.hibernate.dialect.PostgreSQL9Dialect"
        } else if (DatabaseType.MYSQL == dbType) {
            dialect = "org.hibernate.dialect.MySQLDialect"
        } else {
            throw new RuntimeException("Hey! Improve the plugin to detect more dialects!")
        }
        dialect
    }

    private def exportSqlSchema(Configuration hibernateCfg, migrationFilePath) {

        def File migrationFile = new File(migrationFilePath)
        def File migrationDir = migrationFile.getParentFile()
        if (!migrationDir.exists()) {
            migrationDir.mkdirs()
        }

        println "Exporting to: ${migrationFilePath}"
        try {
            def schemaUpdate = new SchemaUpdate(hibernateCfg)
            schemaUpdate.setDelimiter(";")
            schemaUpdate.setOutputFile(migrationFilePath)
            schemaUpdate.execute(true, false)
        } catch (Exception e) {
            e.printStackTrace()
            throw new RuntimeException(e.getMessage(), e)
        }
    }
}

class DdlCodeGenPluginExtension {
    String driverClassName
    String url
    String user
    String password
    String packageName
    String migrationPath
    FileCollection classpath
}
