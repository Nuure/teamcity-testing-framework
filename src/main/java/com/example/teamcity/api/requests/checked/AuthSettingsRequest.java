package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.models.AuthSettings;
import com.example.teamcity.api.requests.Request;
import com.example.teamcity.api.spec.Specifications;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class AuthSettingsRequest extends Request {
    public AuthSettingsRequest(RequestSpecification spec) {
        super(spec);
    }

    public AuthSettings getAuthSettings() {
        return given().spec(Specifications.getSpec().superUserSpec())
                .get("/app/rest/server/authSettings").as(AuthSettings.class);
    }

    public void putAuthSettings(AuthSettings authSettings) {
        given().body(authSettings).put("/app/rest/server/authSettings");
    }
}
