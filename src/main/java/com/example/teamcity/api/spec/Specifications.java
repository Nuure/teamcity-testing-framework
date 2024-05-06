package com.example.teamcity.api.spec;

import com.example.teamcity.api.config.Config;
import com.example.teamcity.api.models.User;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.List;

public class Specifications {
    private static Specifications spec;

    private Specifications() {}

    public static Specifications getSpec() {
        if (spec == null) {
            spec = new Specifications();
        }
        return spec;
    }

    private RequestSpecBuilder requestSpecBuilder() {
        var requestBuilder = new RequestSpecBuilder();
        requestBuilder.addFilters(List.of(new RequestLoggingFilter(), new ResponseLoggingFilter()));
        requestBuilder.setBaseUri("http://" + Config.getProperty("host"));
        requestBuilder.setContentType(ContentType.JSON);
        requestBuilder.setAccept(ContentType.JSON);
        return requestBuilder;
    }

    public RequestSpecification unAuthSpec() {
        var requestBuilder = requestSpecBuilder();
        return requestBuilder.build();
    }

    public RequestSpecification authSpec(User user) {
        var requestBuilder = requestSpecBuilder();
        String base64Credentials = java.util.Base64.getEncoder().encodeToString((user.getUsername()+ ":" + user.getPassword()).getBytes());
        requestBuilder.addHeader("Authorization", "Basic " + base64Credentials);
        return requestBuilder.build();
    }

    public RequestSpecification superUserSpec() {
        var requestBuilder = requestSpecBuilder();
        String base64Credentials = java.util.Base64.getEncoder().encodeToString((":" + Config.getProperty("superUserToken")).getBytes());
        requestBuilder.addHeader("Authorization", "Basic " + base64Credentials);
        return requestBuilder.build();
    }
}
