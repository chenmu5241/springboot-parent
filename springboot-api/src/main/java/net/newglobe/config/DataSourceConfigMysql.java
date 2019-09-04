package net.newglobe.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@MapperScan(basePackages = "mapper", value = "net.newglobe.app.dao", sqlSessionTemplateRef = "sessionTemplateMysql")
public class DataSourceConfigMysql{

	@Bean(name = "dataSourceMysql")
	@ConfigurationProperties(prefix = "spring.datasource.mysql")
	public DataSource dataSourceMysql() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "sessionFactoryMysql")
	public SqlSessionFactory sessionFactory1(@Qualifier("dataSourceMysql") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/mysql/*.xml"));
		return bean.getObject();
	}

	@Bean(name = "transactionManagerMysql")
	public DataSourceTransactionManager transactionManager1(@Qualifier("dataSourceMysql") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean(name = "sessionTemplateMysql")
	public SqlSessionTemplate sessionTemplate1(@Qualifier("sessionFactoryMysql") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

}