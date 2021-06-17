package com.douzone.config.app;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:com/douzone/mysite/config/app/jdbc.properties")
public class DBConfig {
	
	@Autowired
	private Environment env; 
	
	// connection pool datasource
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getProperty("jdbc.url"));
		dataSource.setUsername(env.getProperty("jdbc.username"));
		dataSource.setPassword(env.getProperty("jdbc.password"));
		dataSource.setInitialSize(env.getProperty("jdbc.initialSize",Integer.class)); // connection 초반에 만드는 개수
		dataSource.setMaxActive(env.getProperty("jdbc.maxActice",Integer.class)); //최대 100개 connection까지 만들기
 		return dataSource;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
