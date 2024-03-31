package com.comvarse.microservices.apigateway.configuration;

import com.comvarse.microservices.apigateway.dto.RouteData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Configuration
public class ApiGatewayConfiguration {
    @Autowired
    private RateLimitingConfiguration rateLimitingConfiguration;

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        RouteLocator locator;
        List<Function<PredicateSpec, Buildable<Route>>> routeFunctions = getRouteFunctions();
        RouteLocatorBuilder.Builder routeLocatorBuilderMax = builder.routes();

        for (final Function<PredicateSpec, Buildable<Route>> routeFunction : routeFunctions) {
            routeLocatorBuilderMax.route(routeFunction);
        }

        locator = routeLocatorBuilderMax.build();
        return locator;
    }

    private List<RouteData> getRoutes() {
        List<RouteData> routeData = new ArrayList<RouteData>();
        RouteData rd1 = new RouteData();
        rd1.setId(10001);
        rd1.setDate(LocalDate.now());
        rd1.setPath("/get");
        rd1.setMethod("GET");
        rd1.setUri("http://httpbin.org");
        rd1.setEnv("8000");

        RouteData rd2 = new RouteData();
        rd2.setId(10002);
        rd2.setDate(LocalDate.now());
        rd2.setPath("/cardbacks");
        rd2.setMethod("GET");
        rd2.setUri("https://omgvamp-hearthstone-v1.p.rapidapi.com");
        rd2.setEnv("8000");

        routeData.add(rd1);
        routeData.add(rd2);
        return routeData;
    }
    private List<RouteData> getRouteData() {
        List<RouteData> routeData = new ArrayList<RouteData>();
        ResponseEntity<RouteData[]> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/get-routes", RouteData[].class);
        RouteData[] routeDataList = responseEntity.getBody();
        return Arrays.stream(routeDataList).toList();
    }

    private List<Function<PredicateSpec, Buildable<Route>>> getRouteFunctions() {
        List<Function<PredicateSpec, Buildable<Route>>> routeFunctions = new ArrayList<>();

        List<RouteData> routeDataList = getRouteData();


        for (final RouteData routeData : routeDataList) {
            Function<PredicateSpec, Buildable<Route>> routeFunction =
                    p -> p.path(routeData.getPath())
                            .filters(f -> f.requestRateLimiter(r -> r.setRateLimiter(rateLimitingConfiguration.redisRateLimiter())
                                    .setDenyEmptyKey(false)
                                    .setKeyResolver(rateLimitingConfiguration.apiKeyResolver())))
                            .uri(routeData.getUri());
            System.out.println(routeData.getUri() + routeData.getPath());
            routeFunctions.add(routeFunction);
        }
        return routeFunctions;
    }

}
