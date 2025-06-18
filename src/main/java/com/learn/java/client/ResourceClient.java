package com.learn.java.client;

import com.learn.java.dto.ResourceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "resource",
		url = "${resource.client.url}",
		configuration = ResourceClientConfig.class)
public interface ResourceClient {
	@GetMapping("/internal/res/{id}")
	ResourceDto getResourceDtoById(@PathVariable String id);
}
