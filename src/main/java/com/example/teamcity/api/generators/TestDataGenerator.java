package com.example.teamcity.api.generators;

import com.example.teamcity.api.enums.BuildPropertyNames;
import com.example.teamcity.api.enums.RoleType;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.NewProjectDescription;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.Role;
import com.example.teamcity.api.models.Roles;
import com.example.teamcity.api.models.User;

import java.util.List;

import static com.example.teamcity.api.enums.BuildPropertyNames.SCRIPT_CONTENT;
import static com.example.teamcity.api.enums.BuildPropertyNames.TEAMCITY_STEP_MODE;
import static com.example.teamcity.api.enums.BuildPropertyNames.USE_CUSTOM_SCRIPT;

public class TestDataGenerator {
    public static TestData generate() {
        var user = User.builder()
                .username(RandomData.getString())
                .password(RandomData.getString())
                .email(RandomData.getString() + "@gmail.com")
                .roles(Roles.builder()
                        .role(List.of(Role.builder()
                                .roleId("SYSTEM_ADMIN")
                                .scope("g")
                                .build()))
                        .build())
                .build();
        var project = NewProjectDescription
                .builder()
                .parentProject(Project.builder()
                        .locator("_Root")
                        .build())
                .name(RandomData.getString())
                .id(RandomData.getString())
                .copyAllAssociatedSettings(true)
                .build();

        var buildType = BuildType.builder()
                .id(RandomData.getString())
                .name(RandomData.getString())
                .project(Project.builder()
                        .id(project.getId())
                        .build())
                .build();

        return TestData.builder()
                .user(user)
                .project(project)
                .buildType(buildType)
                .build();
    }

    public static Roles generateRoles(RoleType roleType, String scope) {
        return Roles.builder()
                .role(List.of(Role.builder()
                        .roleId(roleType.getRoleName())
                        .scope(scope)
                        .build()))
                .build();
    }

    public static BuildType generateBuildTypeWithSteps(Project project) {
        return BuildType.builder()
                .id(RandomData.getString())
                .name(RandomData.getString())
                .project(Project.builder()
                        .id(project.getId())
                        .build())
                .steps(BuildType.Steps.builder()
                        .step(List.of(BuildType.Step.builder()
                                .name(RandomData.getString())
                                .type("simpleRunner")
                                .properties(BuildType.Properties.builder()
                                        .property(List.of(generateBuildTypeProperty(SCRIPT_CONTENT, "echo 'Hello World!'"),
                                                generateBuildTypeProperty(TEAMCITY_STEP_MODE, "default'"),
                                                generateBuildTypeProperty(USE_CUSTOM_SCRIPT, "true")))
                                        .build())
                                .build()))
                        .build())
                .build();
    }

    public static BuildType.Property generateBuildTypeProperty(BuildPropertyNames propertyName, String value) {
        return BuildType.Property.builder()
                .name(propertyName.getPropertyName())
                .value(value)
                .build();
    }
}
