package com.example.teamcity.api.enums;

public enum RoleType {
    SYSTEM_ADMIN("SYSTEM_ADMIN"),
    PROJECT_ADMIN("PROJECT_ADMIN"),
    PROJECT_DEVELOPER("PROJECT_DEVELOPER"),
    PROJECT_VIEWER("PROJECT_VIEWER"),
    AGENT_MANAGER("AGENT_MANAGER");

    private final String roleName;

    RoleType(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
