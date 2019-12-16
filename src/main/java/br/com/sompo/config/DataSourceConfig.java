package br.com.sompo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@Profile({"dev", "default"})
public class DataSourceConfig {

	//TODO classe necessaria apenas se for utilizar JDBCTemplate e query no arquivo XML

    @Bean(name = "sompo")
    @ConfigurationProperties(prefix="datasource.sompo") //TODO Indicar o nome da propriedade configurada no application.yml
    public DataSource dsSompo() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "yas")
    @Primary
    @ConfigurationProperties(prefix="datasource.yas") //TODO Indicar o nome da propriedade configurada no application.yml
    public DataSource dsYasnd() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "jdbcSompo")
    @Autowired    
    public JdbcTemplate sompoJdbcTemplate(@Qualifier("sompo") DataSource dsSompo) {
        return new JdbcTemplate(dsSompo);
    }

    @Bean(name = "jdbcYas")
    @Autowired
    public JdbcTemplate yasndJdbcTemplate(@Qualifier("yas") DataSource dsYasnd) {
        return new JdbcTemplate(dsYasnd);
    }	
}