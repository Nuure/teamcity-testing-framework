package com.example.teamcity.api.enums;

public enum BuildPropertyNames {
    SCRIPT_CONTENT("script.content"),
    TEAMCITY_STEP_MODE("teamcity.step.mode"),
    USE_CUSTOM_SCRIPT("use.custom.script");

    private final String propertyName;

    BuildPropertyNames(String roleName) {
        this.propertyName = roleName;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
