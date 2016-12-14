package com.sps.sdp.application.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MyDAO {

	public JdbcTemplate jdbcTemplate;

	@Autowired
	public MyDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public String getUserDetails(String userName) {

		String password;

		String sqlStatement = "SELECT PASSWORD FROM SPSDP_USER_DETL WHERE USER_NAME = :USERNAME";

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("USERNAME", userName);

		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(this.jdbcTemplate.getDataSource());

		password = jdbcTemplate.queryForObject(sqlStatement, parameters, String.class);

		return password;

	}
	
	
	
}
