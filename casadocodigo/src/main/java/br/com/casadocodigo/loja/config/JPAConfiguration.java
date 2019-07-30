package br.com.casadocodigo.loja.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
public class JPAConfiguration {

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManager() {
		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		JpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
		
		entityManager.setJpaVendorAdapter(jpaAdapter);
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUsername(System.getenv("DB_USER"));
		dataSource.setPassword(System.getenv("DB_PWD"));
        dataSource.setUrl("jdbc:mysql://localhost:3306/casadocodigo?useTimezone=true&serverTimezone=UTC&useSSL=false");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        entityManager.setDataSource(dataSource);

        Properties props = new Properties();
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        props.setProperty("hibernate.show_sql", "true");
        props.setProperty("hibernate.hbm2ddl.auto", "update");

        entityManager.setJpaProperties(props);

        entityManager.setPackagesToScan("br.com.casadocodigo.loja.models");

        return entityManager;	
	}
	
    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf){
        return new JpaTransactionManager(emf);
    }
}
