package com.example.findjob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.example.findjob.filter.CookieAuthenticationFilter;

@SpringBootApplication
public class FindjobApplication {

	public static void main(String[] args) {
		SpringApplication.run(FindjobApplication.class, args);
	}

	@Bean
    public FilterRegistrationBean<CookieAuthenticationFilter> loggingFilter(CookieAuthenticationFilter filter) {
        FilterRegistrationBean<CookieAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/findjob/*");

        return registrationBean;
    }
}
