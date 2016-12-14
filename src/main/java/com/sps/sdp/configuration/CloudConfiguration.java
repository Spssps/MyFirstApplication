package com.sps.sdp.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("cloud")
@PropertySource(value = "classpath:application.yml")
public class CloudConfiguration {

	@Bean
	public Cloud cloud() {
		return new CloudFactory().getCloud();
	}
	
	@Autowired
	private SPSDPConfigurationProperties spsdpConfigurationProperties;
	
	@Bean
	public DataSource getDataSource() {

		DataSource ds = getDataSourceInfoFromCloud(spsdpConfigurationProperties.getClearDbDataSourceName());

		return ds;
	}

	private DataSource getDataSourceInfoFromCloud(String serviceName) {
		String vcapServices = System
				.getenv("VCAP_SERVICES");

		return DataSourceLoader.load(vcapServices, serviceName);
	}
	
}
