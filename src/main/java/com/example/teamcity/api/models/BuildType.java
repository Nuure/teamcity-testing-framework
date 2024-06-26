package com.example.teamcity.api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildType {
    private String id;
    private Project project;
    private String name;
    private Steps steps;

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static public class Steps {
        private List<Step> step;
    }

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static public class Step {
        private String name;
        private String type;
        private Properties properties;
    }

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static public class Properties {
        private List<Property> property;
    }

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static public class Property {
        private String name;
        private String value;
    }
}
