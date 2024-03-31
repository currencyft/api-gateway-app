package com.comvarse.microservices.routeservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity(name = "route_data")
public class RouteData {
    @Id
    @GeneratedValue
    private Integer id;

    @JsonProperty("date")
    @Column(name = "date")
    private LocalDate date;

    @JsonProperty("path")
    @Column(name = "path")
    private String path;

    @JsonProperty("method")
    @Column(name = "method")
    private String method;

    @JsonProperty("uri")
    @Column(name = "uri")
    private String uri;

    @JsonProperty("env")
    @Column(name = "env")
    private String env;

    public RouteData() {
    }

    public RouteData(Integer id, LocalDate date, String path, String method, String uri, String env) {
        this.id = id;
        this.date = date;
        this.path = path;
        this.method = method;
        this.uri = uri;
        this.env = env;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }
}
