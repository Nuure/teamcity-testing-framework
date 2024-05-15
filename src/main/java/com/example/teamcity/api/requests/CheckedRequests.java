package com.example.teamcity.api.requests;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.requests.checked.CheckedBase;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;

@Getter
public class CheckedRequests {
//    private CheckedUser userRequest;
//    private CheckedProject projectRequest;
//    private CheckedBuildConfig buildConfigRequest;

    private CheckedBase checkedBase;

//    public CheckedRequests(RequestSpecification spec) {
//        this.userRequest = new CheckedUser(spec);
//        this.projectRequest = new CheckedProject(spec);
//        this.buildConfigRequest = new CheckedBuildConfig(spec);
//    }

    public CheckedRequests(RequestSpecification spec, Endpoint endpoint) {
        this.checkedBase = new CheckedBase(spec, endpoint);
    }
}
