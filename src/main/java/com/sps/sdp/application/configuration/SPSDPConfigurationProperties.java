package com.sps.sdp.application.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spsdp")
public class SPSDPConfigurationProperties {

	private String clearDbDataSourceName;

	public String getClearDbDataSourceName() {
		return clearDbDataSourceName;
	}

	public void setClearDbDataSourceName(String clearDbDataSourceName) {
		this.clearDbDataSourceName = clearDbDataSourceName;
	}
		
}
