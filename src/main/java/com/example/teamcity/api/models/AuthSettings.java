package com.example.teamcity.api.models;

import lombok.Data;

import java.util.List;

@Data
public class AuthSettings {
    private boolean allowGuest;
    private String guestUsername;
    private String welcomeText;
    private boolean collapseLoginForm;
    private boolean perProjectPermissions;
    private boolean emailVerification;
    private Modules modules;

    static class Modules {
        private List<Module> module;
    }

    static class Module {
        private String name;
        private Properties properties;
    }

    static class Properties {
        private List<Property> property;
    }

    static class Property {
        private String name;
        private String value;
    }
}
