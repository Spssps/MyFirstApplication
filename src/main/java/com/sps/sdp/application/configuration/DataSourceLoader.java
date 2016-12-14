package com.sps.sdp.application.configuration;

import java.util.Map;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

public class DataSourceLoader {

	public static enum DBServiceKeys {
		DRIVER_CLASS_NAME("driverClassName"), PASSWORD("password"), JDBC_URL("jdbcUrl"), USER_NAME(
				"username"), CONN_TEST_QUERY(
						"connectionTestQuery"), MAX_POOL_SIZE("maximumPoolSize"), CONN_TIME_OUT("connectionTimeout");

		private String value;

		private DBServiceKeys(String value) {
			this.value = value;
		}

		public String value() {
			return value;
		}
	}

	public static DataSource load(String vcapServices, String serviceName) {
		Map<String, Object> credentialMap = CloudServiceLoader.getCredentials(vcapServices, serviceName);
		HikariDataSource dataSource = new HikariDataSource();

		if (credentialMap.containsKey(DBServiceKeys.USER_NAME.value())) {
			dataSource.setUsername((String) credentialMap.get(DBServiceKeys.USER_NAME.value()));
		}

		if (credentialMap.containsKey(DBServiceKeys.PASSWORD.value())) {
			dataSource.setPassword((String) credentialMap.get(DBServiceKeys.PASSWORD.value()));
		}

		if (credentialMap.containsKey(DBServiceKeys.JDBC_URL.value())) {
			dataSource.setJdbcUrl((String) credentialMap.get(DBServiceKeys.JDBC_URL.value()));
		}

		if (credentialMap.containsKey(DBServiceKeys.DRIVER_CLASS_NAME.value())) {
			dataSource.setDriverClassName((String) credentialMap.get(DBServiceKeys.DRIVER_CLASS_NAME.value()));
		} else {
			dataSource.setDriverClassName(getDriverClassName(dataSource.getJdbcUrl()));
		}

		if (credentialMap.containsKey(DBServiceKeys.CONN_TEST_QUERY.value())) {
			dataSource.setConnectionTestQuery((String) credentialMap.get(DBServiceKeys.CONN_TEST_QUERY.value()));
		} else {
			dataSource.setConnectionTestQuery(getConnectionTestQuery(dataSource.getJdbcUrl()));
		}

		if (credentialMap.containsKey(DBServiceKeys.MAX_POOL_SIZE.value())) {
			dataSource.setMaximumPoolSize(((Double) credentialMap.get(DBServiceKeys.MAX_POOL_SIZE.value())).intValue());
		} else {
			dataSource.setMaximumPoolSize(10);
		}

		if (credentialMap.containsKey(DBServiceKeys.CONN_TIME_OUT.value())) {
			dataSource.setConnectionTimeout(
					((Double) credentialMap.get(DBServiceKeys.CONN_TIME_OUT.value())).longValue());
		} else {
			dataSource.setConnectionTimeout(30000L);
		}
		return dataSource;
	}

	private static String getDriverClassName(String jdbcUrl) {
		String driverClassName = null;
		if (jdbcUrl != null) {
			if (jdbcUrl.startsWith(DriverClassName.DB2.jdbcUrlKey())) {
				driverClassName = DriverClassName.DB2.value();
			} else if (jdbcUrl.startsWith(DriverClassName.ORACLE.jdbcUrlKey())) {
				driverClassName = DriverClassName.ORACLE.value();
			} else if (jdbcUrl.startsWith(DriverClassName.MYSQL.jdbcUrlKey())) {
				driverClassName = DriverClassName.MYSQL.value();
			}
		}
		return driverClassName;
	}

	public static enum DriverClassName {
		DB2("com.ibm.db2.jcc.DB2Driver", "jdbc:db2", "select 1 from sysibm.sysdummy1 with ur"), ORACLE(
				"oracle.jdbc.driver.OracleDriver", "jdbc:oracle",
				"SELECT 1 FROM DUAL"), MYSQL("com.mysql.jdbc.Driver", "jdbc:mysql", "SELECT 1");

		private String value;

		private String jdbcUrlKey;
		private String connectionTestQuery;

		private DriverClassName(String value, String jdbcUrlKey, String connectionTestQuery) {
			this.value = value;
			this.jdbcUrlKey = jdbcUrlKey;
			this.connectionTestQuery = connectionTestQuery;
		}

		public String value() {
			return value;
		}

		public String jdbcUrlKey() {
			return jdbcUrlKey;
		}

		public String connectionTestQuery() {
			return connectionTestQuery;
		}
	}

	private static String getConnectionTestQuery(String jdbcUrl) {
		String connectionTestQuery = null;
		if (jdbcUrl != null) {
			if (jdbcUrl.startsWith(DriverClassName.DB2.jdbcUrlKey())) {
				connectionTestQuery = DriverClassName.DB2.connectionTestQuery();
			} else if (jdbcUrl.startsWith(DriverClassName.ORACLE.jdbcUrlKey())) {
				connectionTestQuery = DriverClassName.ORACLE.connectionTestQuery();
			} else if (jdbcUrl.startsWith(DriverClassName.MYSQL.jdbcUrlKey())) {
				connectionTestQuery = DriverClassName.MYSQL.connectionTestQuery();
			}
		}
		return connectionTestQuery;
	}

}
