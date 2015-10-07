package com.github.mrrigby.trueinvoices.infrastructure.test
import groovy.xml.StreamingMarkupBuilder
import org.dbunit.DataSourceDatabaseTester
import org.dbunit.IDatabaseTester
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder
import org.dbunit.operation.DatabaseOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import spock.lang.Specification

import javax.sql.DataSource

/**
 * @author MrRigby
 */
class DbDrivenSpec extends Specification {

    @Autowired
    DataSource dataSource

    IDatabaseTester databaseTester

    def setup() {
        dataSource != null
        if (!databaseTester) {
            databaseTester = new DataSourceDatabaseTester(new TransactionAwareDataSourceProxy(dataSource))
        }
    }

    def cleanup() {
        databaseTester.onTearDown()
    }

    def dataSet = { data,
                    setUpOperation = DatabaseOperation.CLEAN_INSERT,
                    teraDownOperation = DatabaseOperation.NONE ->

        databaseTester.dataSet = dbUnitDataSet data
        databaseTester.setUpOperation = setUpOperation
        databaseTester.tearDownOperation = teraDownOperation
        databaseTester.onSetup()
    }

    def dbUnitDataSet = { data ->
        def xmlData = new StreamingMarkupBuilder().bind { dataset data}
        def xmlReader = new StringReader(xmlData.toString())
        new FlatXmlDataSetBuilder().build(xmlReader)
    }
}
