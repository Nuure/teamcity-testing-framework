package com.example.teamcity.api.requests;

import com.example.teamcity.api.enums.Endpoint;
import io.restassured.specification.RequestSpecification;

public class Request {
    protected RequestSpecification spec;
    protected Endpoint endpoint;

//    public Request(RequestSpecification spec) {
//        this.spec = spec;
//    }

    public Request(RequestSpecification spec, Endpoint endpoint) {
        this.spec = spec;
        this.endpoint = endpoint;
    }
}
