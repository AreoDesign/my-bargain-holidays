package com.areo.design.holidays.service.request.strategy;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.net.URI;

public interface Request {
    URI getUri();

    HttpMethod getHttpMethod();

    <V> HttpEntity<V> getHttpEntity();

    <T> ParameterizedTypeReference<T> getResponseType();
}
