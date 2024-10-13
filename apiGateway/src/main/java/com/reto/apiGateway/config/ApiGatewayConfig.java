package com.reto.apiGateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfig {
	@Bean
	public RouteLocator customRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("backend1", r -> r.path("/products/**")
						.uri("http://backend1:8081"))
				.route("backend2", r -> r.path("/notifications/**")
						.uri("http://backend2:8082"))
				.route("socket", r -> r.path("/ws/**")
						.filters(f -> f.setRequestHeader("Sec-WebSocket-Protocol", ""))
						.uri("ws://backend2:8082"))
				.build();
	}
}
