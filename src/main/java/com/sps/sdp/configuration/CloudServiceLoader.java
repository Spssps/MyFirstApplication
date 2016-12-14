package com.sps.sdp.configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class CloudServiceLoader {
	
	private static final List<String> SERVICE_NODES = Arrays
			.asList(new String[] { "user-provided", "p-mysql", "p-redis" });

	private static final String SERVICE_KEY_NAME = "name";

	private static final String CRED_KEY_NAME = "credentials";

	public static Map<String, Object> getCredentials(String vcapServices, String serviceName) {
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap();
		List<Map<String, Object>> services = null;
		Map<String, Object> credentialMap = null;
		String serviceValue = null;
		try {
			map = (Map) gson.fromJson(vcapServices, map.getClass());
			if (map != null) {
				for (String node : SERVICE_NODES) {
					if (map.containsKey(node)) {
						services = (List) map.get(node);
						if (services != null) {
							for (Map<String, Object> service : services) {
								if (service.containsKey(SERVICE_KEY_NAME)) {
									serviceValue = (String) service.get(SERVICE_KEY_NAME);
									if (serviceName.equals(serviceValue)) {
										if (service.containsKey(CRED_KEY_NAME)) {
											return (Map) service.get(CRED_KEY_NAME);
										}

									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new IllegalStateException("Error parsing VCAP Services", e);
		}
		return credentialMap;
	}

}
