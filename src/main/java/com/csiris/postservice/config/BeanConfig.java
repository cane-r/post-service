package com.csiris.postservice.config;

import static java.util.Optional.ofNullable;

import java.lang.reflect.Field;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.MethodParameter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.csiris.postservice.persistence.Post;
import com.csiris.postservice.service.PostService;

@Configuration
public class BeanConfig {

	@Bean
	public Random random() {
		return new Random();
	}

	@Bean
	@Scope("prototype")
	public Logger logger(final InjectionPoint ip) {
		return LoggerFactory.getLogger(ofNullable(ip.getMethodParameter())
				.<Class<?>>map(MethodParameter::getContainingClass).orElseGet(() -> ofNullable(ip.getField())
						.map(Field::getDeclaringClass).orElseThrow(IllegalArgumentException::new)));
	}

	@Bean
	public ApplicationRunner initialize(PostService service, Logger logger) {
		return new ApplicationRunner() {
			@Override
			public void run(ApplicationArguments args) throws Exception {
				logger.info(String.format("Contains java runtime arg named \"test\" ? : %s , value : %s",
						args.containsOption("test"), args.getOptionValues("test")));

				Post post1 = new Post("Title-1", "Content-1");
				post1.setApproved(true);
				service.savePost(post1);

				Post post2 = new Post("Title-2", "Content-2");
				//post2.setApproved(true);
				service.savePost(post2);

				logger.info("Started..");
			}
		};
	}

	@Bean
	public LocalValidatorFactoryBean validator(MessageSource messageSource) {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource);
		return bean;
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
}