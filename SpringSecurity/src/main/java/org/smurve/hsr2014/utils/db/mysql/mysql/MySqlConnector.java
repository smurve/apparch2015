package org.smurve.hsr2014.utils.db.mysql.mysql;

import com.mysql.jdbc.Driver;
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smurve.hsr2014.utils.db.DatabaseConnector;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Properties;

@Component
public class MySqlConnector implements DatabaseConnector {

    private final static Logger LOGGER = LoggerFactory.getLogger(MySqlConnector.class);

    @Override
    public DataSource buildDataSource() {
        MySqlConnectionParams params = new MySqlConnectionParams();
        BasicDataSource retval = new BasicDataSource();

        retval.setDriverClassName(Driver.class.getName());
        retval.setUrl(params.jdbcUrl);
        retval.setUsername(params.jdbcUsername);
        retval.setPassword(params.jdbcPassword);

        LOGGER.info("DataSource set up with params: " + params.toString());
        return retval;
    }

    @Override
    public void appendVendorJpaConfiguration(Properties properties) {
        properties.setProperty("hibernate.dialect", MySQL5InnoDBDialect.class.getName());
    }

    @Override
    public boolean supportsCreateAndDrop() {
        return false;
    }
}
