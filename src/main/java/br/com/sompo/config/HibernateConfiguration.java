package br.com.sompo.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
 
@Configuration
@EnableTransactionManagement
@DependsOn("hibernatePersistenceProviderResolver")
@PropertySource("classpath:application.yml")
@Profile("prod")
public class HibernateConfiguration {
	
	//TODO Classe de configuracao do WebSphere para conexao com o banco de dados
	//TODO Caso for utilizar mais de um datasource, criar outra classe dessa alterando o nome do datasource
 
	@Autowired
	private Environment environment;
 
	/**
	 * Bean definition to configure datasource for Hibernate SessionFactory
	 * Either if you wanna read by DataSource, OK, or read by JDBC, its ok as well.
	 * 
	 * @return DataSource
	 */
	@Bean(name = "sompo")
	public DataSource dataSource() {
		
		JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
		JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
		//TODO colocar nome do datasource que será utilizado no WebSphere
		jndiObjectFactoryBean.setJndiName("jdbc/ConsultaApolicesItensMaritimaDS");
		return dataSourceLookup.getDataSource("jdbc/ConsultaApolicesItensMaritimaDS");
 
	}
	
	@Bean(name = "jdbcSompo")
    @Autowired    
    public JdbcTemplate sompoJdbcTemplate(@Qualifier("sompo") DataSource dsSompo) {
        return new JdbcTemplate(dsSompo);
    }
 
	/**
	 * This method is used to set the properties for Hibernate configuration
	 * 
	 * @return Properties
	 */
	private Properties hibernateProperties() {
		final Properties properties = new Properties();
		properties.put("hibernate.dialect", environment.getRequiredProperty("spring.jpa.hibernate.dialect"));
		properties.put("hibernate.show_sql",environment.getRequiredProperty("spring.jpa.show-sql"));
		properties.put("hibernate.format_sql",environment.getRequiredProperty("spring.jpa.properties.hibernate.format_sql"));
 
		return properties;
	}
 
	/**
	 * Bean definition to configure Hibernate Transaction manager
	 * 
	 * @param SessionFactory sessionFactory
	 * @return
	 */
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(final SessionFactory sessionFactory) {
		final HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);
		return txManager;
	}
 
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		//TODO alterar nome do pacote onde estao os models
		em.setPackagesToScan(new String[] { "br.com.sompo.example.model" });
 
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(hibernateProperties());
 
		return em;
	}
 
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
 
		return transactionManager;
	}
 
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
 
}
