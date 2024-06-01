package com.example.teamcity.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthSettings {
    private boolean allowGuest;
    private String guestUsername;
    private String welcomeText;
    private boolean collapseLoginForm;
    private boolean perProjectPermissions;
    private boolean emailVerification;
    private Modules modules;

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Modules {
        private List<Module> module;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Module {
        private String name;
        private Properties properties;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Properties {
        private List<Property> property;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Property {
        private String name;
        private String value;
    }
}
