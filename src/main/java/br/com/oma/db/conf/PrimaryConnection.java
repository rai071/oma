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
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "sqlServerEntityManagerFactory", transactionManagerRef = "sqlServerTransactionManager", basePackages = "br.com.oma.primary.repository")
public class PrimaryConnection {

	/*@Value("${spring.jpa.hibernate.ddl-auto}")
	private String hibernate;*/

	@Value("${primary.datasource.driverClassName}")
	private String driverClassName;

	@Value("${primary.datasource.url}")
	private String url;

	@Value("${primary.datasource.password}")
	private String password;

	@Value("${primary.datasource.username}")
	private String username;

	@Primary
	@Bean(name = "sqlServerDataSource")
	@ConfigurationProperties(prefix = "primary.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().driverClassName(driverClassName).url(url).password(password)
				.username(username).build();
	}

	@Primary
	@Bean(name = "sqlServerEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("sqlServerDataSource") DataSource dataSource) {
		Map<String, String> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.dialect", "org.hibernate.dialect.SQLServer2008Dialect");
		return builder.dataSource(dataSource).properties(properties).packages("br.com.oma.primary.*")
				.persistenceUnit("primeiroDb").build();
	}

	@Primary
	@Bean(name = "sqlServerTransactionManager")
	public PlatformTransactionManager transactionManager(
			@Qualifier("sqlServerEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
