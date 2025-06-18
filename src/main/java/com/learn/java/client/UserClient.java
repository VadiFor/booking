package com.learn.java.client;

import com.learn.java.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user",
		url = "${user.client.url}",
		configuration = UserClientConfig.class)
public interface UserClient {
	@GetMapping("/internal/usr/{id}")
	UserDto getUserDtoById(@PathVariable String id);
}
