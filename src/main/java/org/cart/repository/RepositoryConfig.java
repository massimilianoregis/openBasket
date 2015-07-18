package org.cart.repository;


import java.sql.Statement;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@PropertySource({"basket.properties"})
@Configuration
@Component("repositoryConfigCart")
@EnableJpaRepositories(
		basePackages = "org.cart",
		entityManagerFactoryRef="entityManagerCartFactory",
		transactionManagerRef="cartTransactionManager"
		)
public class RepositoryConfig {
	
	private DriverManagerDataSource datasource;
	@Value("${basket.db.url}") private String dburl;
	@Value("${basket.db.driver}") private String dbDriver;
	@Value("${basket.db.user}") private String dbUser;
	@Value("${basket.db.psw}") private String dbPsw;
	@Value("${basket.db.dialect}") private String dbDialect;
	@Value("${basket.db.create.url}") private String createUrl;
	@Value("${basket.db.create.cmd}") private String createCmd;

	public DataSource dataSource() {
		  	//System.out.println("datasource:"+servletContext.getRealPath("WEB-INF/data"));		
		datasource = new DriverManagerDataSource();
	        datasource.setDriverClassName(dbDriver);
	        datasource.setUrl(dburl);
	        datasource.setUsername(dbUser);
	        datasource.setPassword(dbPsw);	
		try	{
			datasource.getConnection();
			}
        catch(Exception e)
        	{        
        	DriverManagerDataSource newdatasource = new DriverManagerDataSource();
	        	newdatasource.setDriverClassName(dbDriver);
	        	newdatasource.setUrl(createUrl);
	        	newdatasource.setUsername(dbUser);
	        	newdatasource.setPassword(dbPsw);
	        	Statement statement = null; 
	        	try	{
	        		statement = newdatasource.getConnection().createStatement();
	        		statement.execute(createCmd);
	        		statement.close();
	        		}
	        	catch(Exception ex)
	        		{        		
	        		ex.printStackTrace();
	        		}
        	}
	        
	        return datasource;
	    }
	
		
	  @Bean 	  
	  public LocalContainerEntityManagerFactoryBean entityManagerCartFactory() {
		  System.out.println("\n\n*****************\nCart\nrepositoryConfig\nEntity Manager\n**********************\n\n");
		  
		  JpaVendorAdapter vendorAdapter = jpaVendorAdapter();
		  LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		    
		  Properties jpaProperties = new Properties();
		    		 jpaProperties.setProperty("hibernate.hbm2ddl.auto", "update");		
		    		 
		  
		  factory.setDataSource(dataSource());
		  factory.setJpaProperties(jpaProperties);
		  factory.setJpaVendorAdapter(vendorAdapter);
		  factory.setPackagesToScan("org.cart");		  
		  factory.setPersistenceUnitName("basket");
		  

		  factory.afterPropertiesSet();

		  return factory;
		  }
	  

    public JpaVendorAdapter jpaVendorAdapter() 
		{					
		final HibernateJpaVendorAdapter rv = new HibernateJpaVendorAdapter();
		
		if(this.dbDialect!=null) rv.setDatabasePlatform(this.dbDialect);
		
		rv.setGenerateDdl(false);
		rv.setShowSql(true);
		
		return rv;
	    }
	
    @Bean(name = "cartTransactionManager")
    public PlatformTransactionManager transactionManager(){
    	
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerCartFactory().getObject());

        return transactionManager;
    }


}
