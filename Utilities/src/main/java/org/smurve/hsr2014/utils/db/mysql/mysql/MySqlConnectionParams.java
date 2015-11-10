package org.smurve.hsr2014.utils.db.mysql.mysql;

class MySqlConnectionParams {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "root";

    public final String jdbcUrl = JDBC_URL;
    public final String jdbcUsername = JDBC_USER;
    public final String jdbcPassword = JDBC_PASSWORD;

    public MySqlConnectionParams() {
    }

    @Override
    public String toString() {
        return String.format("url:%s   user:%s ", jdbcUrl, jdbcUsername);
    }
}
