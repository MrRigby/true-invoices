package com.github.mrrigby.trueinvoices.common.test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import spock.lang.Specification

import javax.sql.DataSource
/**
 * @author MrRigby
 */
class DbDrivenSpec extends Specification {

    @Autowired
    DataSource dataSource

    @Autowired
    ApplicationContext ctx

}
