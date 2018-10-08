package com.bbg.pojo;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DataSourceConfig {
	
	@Bean(name="bhbi")
	@ConfigurationProperties(prefix="spring.datasource.bhbi")
	public DataSource bhbi(){
		return  DataSourceBuilder.create().build();
	}
	
	@Bean(name="jdbcTemplate")
	public JdbcTemplate bhbiJdbcTemplate(@Qualifier("bhbi") DataSource dataSource){
		return new JdbcTemplate(dataSource);
	}
	
	@Bean(name="mobi")
	@ConfigurationProperties(prefix="spring.datasource.mobi")
	public DataSource mobi(){
		return  DataSourceBuilder.create().build();
	}
	
	@Bean(name="mobiJdbcTemplate")
	public JdbcTemplate mobiJdbcTemplate(@Qualifier("mobi") DataSource dataSource){
		return new JdbcTemplate(dataSource);
	}


}
