package org.jsp.allocationservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;


import javax.sql.DataSource;

@Configuration

public class Config  {

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public RestTemplate getrestTemplate() {
        return new RestTemplate();
    }

}
