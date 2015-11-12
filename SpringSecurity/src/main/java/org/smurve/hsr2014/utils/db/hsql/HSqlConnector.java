package org.smurve.hsr2014.utils.db.hsql;

import org.hibernate.dialect.HSQLDialect;
import org.smurve.hsr2014.utils.db.DatabaseConnector;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Do not annotate with @Component.
 * <p>
 * ApplicationContext should not auto-create this bean.
 */
public class HSqlConnector implements DatabaseConnector {
    @Override
    public DataSource buildDataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).build();
    }

    @Override
    public void appendVendorJpaConfiguration(Properties properties) {
        properties.setProperty("hibernate.dialect", HSQLDialect.class.getName());
    }

    @Override
    public boolean supportsCreateAndDrop() {
        return false;
    }
}
