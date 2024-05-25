package com.example.teamcity.ui.enums;

import lombok.Getter;

@Getter
public enum Errors {
    ERROR_URL("error_url", "URL must not be empty"),
    ERROR_BUILD_NAME("error_buildTypeName", "Build configuration name must not be empty"),
    ERROR_PROJECT_NAME("error_projectName", "Project name must not be empty"),
    ERROR_BRANCH("error_branch", "Branch name must be specified");

    private final String errorType;
    private final String text;

    Errors(String errorType, String text) {
        this.errorType = errorType;
        this.text = text;
    }
}
