package com.comvarse.microservices.routeinvocation.controller;

import com.comvarse.microservices.routeinvocation.dto.RouteData;
import com.comvarse.microservices.routeinvocation.dto.RouteInvokeProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class RouteInvokeController {

    Logger logger = LoggerFactory.getLogger(RouteInvokeController.class);

    @Autowired
    private RouteInvokeProxy proxy;

    public RouteInvokeController() {

    }

    @GetMapping("/invoke-route-service")
    public List<RouteData> invokeRoutes() {
        /**
         * here array, RouteData[] is used instead of list to accomodate the second
         * parameter for .getForEntity(url, class) since List<RouteData> cannot be used
        */
        ResponseEntity<RouteData[]> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/get-routes", RouteData[].class);
        RouteData[] routeDataList = responseEntity.getBody();
        for (RouteData routeData : routeDataList) {
            /*Modify Environment*/
            String modifiedEnv = routeData.getEnv() + " rest template";
            routeData.setEnv(modifiedEnv);
        }
        return Arrays.stream(routeDataList).toList();
    }

    @GetMapping("/invoke-route-service-feign")
    public List<RouteData> invokeRoutesFeign() {
        logger.info("Route Service Invocation Placed.");
        List<RouteData> routeDataList = proxy.retrieveRoutes();
        for (RouteData routeData : routeDataList) {
            /*Modify Environment*/
            String modifiedEnv = routeData.getEnv() + " feign";
            routeData.setEnv(modifiedEnv);
        }
        return routeDataList;
    }
}
