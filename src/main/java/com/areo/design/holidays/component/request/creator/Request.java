package com.areo.design.holidays.component.request.creator;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.net.URI;

public interface Request {
    URI getUri();

    HttpMethod getHttpMethod();

    HttpEntity<String> getHttpEntity();

    <T> ParameterizedTypeReference<T> getResponseType();
}
