package org.smurve.hsr2014.utils.db;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Sets up the database connection to be used by the application.
 */
public interface DatabaseConnector {
    DataSource buildDataSource();

    void appendVendorJpaConfiguration(Properties properties);

    boolean supportsCreateAndDrop();
}
