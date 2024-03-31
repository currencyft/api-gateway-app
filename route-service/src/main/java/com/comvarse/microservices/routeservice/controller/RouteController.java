package com.comvarse.microservices.routeservice.controller;

import com.comvarse.microservices.routeservice.entity.RouteData;
import com.comvarse.microservices.routeservice.repository.RouteRepository;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RouteController {

    private Logger logger = LoggerFactory.getLogger(RouteController.class);

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private Environment environment;

    @GetMapping("/get-routes")
    //@Retry(name = "max-retry", fallbackMethod = "onFailure") //e.g. retry only 5 times
    //@CircuitBreaker(name = "default", fallbackMethod = "onFailure") //e.g. on permanent downtime, permanently stop all incoming calls
    //@RateLimiter(name = "default", fallbackMethod = "onFailure") //e.g. 10000 calls every 10 seconds
    @Bulkhead(name = "default", fallbackMethod = "onFailure")
    public ResponseEntity<List<RouteData>> retrieveRoutes() {

        String localPort = environment.getProperty("local.server.port");

        logger.info("Route data extraction placed: {}", localPort);

        List<RouteData> routeData = routeRepository.findAll();
        for (RouteData roda : routeData) {
            roda.setEnv(localPort);
        }

        //ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url", String.class);

        return ResponseEntity.ok(routeData);

    }

    @GetMapping("/sampleapi")
    @Retry(name = "max-retry", fallbackMethod = "onFailure")
    public ResponseEntity<String> sampleApi() {
        logger.info("sample api called.");
        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url", String.class);
        return ResponseEntity.ok(forEntity.getBody());
    }

    public ResponseEntity<String> onFailure(Exception ex) {
        String failureResponse = addErrorPrefix(ex.getMessage());
        return ResponseEntity.ok(failureResponse);
    }

    public String addErrorPrefix(String errorMessage) {
        return String.format("ERROR: %s", errorMessage);
    }
}
