package com.comvarse.microservices.routeservice.repository;

import com.comvarse.microservices.routeservice.entity.RouteData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<RouteData, Integer> {
}
