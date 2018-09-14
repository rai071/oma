package br.com.oma.db.conf;


import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "secondarySqlServerEntityManagerFactory", transactionManagerRef = "secondarySqlServerTransactionManager", basePackages = "br.com.oma.secondary.repository")
public class SecondaryConnetion {

/*	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String hibernate;*/

	@Value("${secondary.datasource.driverClassName}")
	private String driverClassName;

	@Value("${secondary.datasource.url}")
	private String url;

	@Value("${secondary.datasource.password}")
	private String password;

	@Value("${secondary.datasource.username}")
	private String username;

	@Bean(name = "secondarySqlServerDataSource")
	@ConfigurationProperties(prefix = "secondary.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().driverClassName(driverClassName).url(url).password(password)
				.username(username).build();
	}

	@Bean(name = "secondarySqlServerEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("secondarySqlServerDataSource") DataSource dataSource) {
		Map<String, String> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.dialect", "org.hibernate.dialect.SQLServer2008Dialect");
		return builder.dataSource(dataSource).properties(properties).packages("br.com.oma.secondary.*")
				.persistenceUnit("segundoDb").build();
	}

	@Bean(name = "secondarySqlServerTransactionManager")
	public PlatformTransactionManager transactionManager(
			@Qualifier("secondarySqlServerEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
