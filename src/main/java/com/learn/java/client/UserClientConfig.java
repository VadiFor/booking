package com.learn.java.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserClientConfig implements RequestInterceptor {
	@Value("${user.internal.token}")
	private String internalToken;
	
	@Override
	public void apply(RequestTemplate requestTemplate) {
		requestTemplate.header("Internal-Token", internalToken);
	}
}
