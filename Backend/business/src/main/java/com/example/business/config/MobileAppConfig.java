package com.example.business.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

//@Configuration
//@EnableWebMvc
//@EnableSpringDataWebSupport
public class MobileAppConfig extends WebMvcConfigurationSupport {
//
//	  @Override
//	  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//	    converters.add((HttpMessageConverter<?>) new PageableHandlerMethodArgumentResolver());
//	  }
}