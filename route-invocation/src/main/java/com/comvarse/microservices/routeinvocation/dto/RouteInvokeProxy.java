package com.comvarse.microservices.routeinvocation.dto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @FeignClient(name = "route-service", url = "localhost:8000")
 * feign asks for the instances of the route-service microservice and load balances them
 * */
@FeignClient(name = "route-service")
public interface RouteInvokeProxy {
    @GetMapping("/get-routes") /* this is the url being invoked on the route-service */
    public List<RouteData> retrieveRoutes();
}