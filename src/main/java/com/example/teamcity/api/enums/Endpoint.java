package com.example.teamcity.api.enums;

import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.User;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public enum Endpoint {
    PROJECT_ENDPOINT("/app/rest/projects", Project.class, "/id:"),
    USER_ENDPOINT("/app/rest/users", User.class, "/username:"),
    BUILD_CONFIG_ENDPOINT("/app/rest/buildTypes", BuildType.class, "/id:");

    private final String endpoint;
    private final Class<?> clazz;
    private final String locator;

    Endpoint(String endpoint, Class<?> clazz, String locator) {
        this.endpoint = endpoint;
        this.clazz = clazz;
        this.locator = locator;
    }
}
