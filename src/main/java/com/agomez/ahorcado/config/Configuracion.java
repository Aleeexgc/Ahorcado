package com.agomez.ahorcado.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.unit.DataSize;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.servlet.MultipartConfigElement;

@Configuration
public class Configuracion {
	
//	  @Bean
//	    public MessageSource messageSource() {
//	        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//	        messageSource.setBasename("classpath:messages");
//	        messageSource.setDefaultEncoding("UTF-8");
//	        return messageSource;
//	    }

//	    @Bean
//	    public LocalValidatorFactoryBean getValidator() {
//	        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
//	        bean.setValidationMessageSource(messageSource());
//	        return bean;
//	    }
//
//	    @Bean
//	    public MultipartConfigElement multipartConfigElement(){
//	        MultipartConfigFactory factory=new MultipartConfigFactory();
//	        factory.setMaxFileSize(DataSize.ofKilobytes(300));
//	        factory.setMaxRequestSize(DataSize.ofKilobytes(300));
//	        return factory.createMultipartConfig();
//	    }
}
