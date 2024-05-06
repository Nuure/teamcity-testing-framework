package com.example.teamcity.api;

import com.example.teamcity.api.generators.TestData;
import com.example.teamcity.api.models.Project;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BuildConfigurationNegativeTest extends BaseApiTest {

    private TestData testData;
    private Project project;

    @BeforeMethod
    public void prepareTestData() {
        testData = testDataStorage.addTestData();
        project = checkedWithSuperUser.getProjectRequest().create(testData.getProject());
    }

    @Test
    public void canNotCreateTwoBuildConfigurationsWithTheSameIdEvenInDifferentProjects() {
        TestData secondTestData = testDataStorage.addTestData();

        String firstBuildConfigId = testData.getBuildType().getId();
        secondTestData.getBuildType().setId(firstBuildConfigId);

        checkedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType());
        checkedWithSuperUser.getProjectRequest().create(secondTestData.getProject());
        uncheckedWithSuperUser.getBuildConfigRequest().create(secondTestData.getBuildType())
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("The build configuration / template ID \"" + firstBuildConfigId
                        + "\" is already used by another configuration or template"));
    }

    @Test
    public void canNotCreateTwoBuildConfigurationsWithTheSameNameInOneProject() {
        TestData secondTestData = testDataStorage.addTestData();

        String firstBuildConfigName = testData.getBuildType().getName();
        secondTestData.getBuildType().setName(firstBuildConfigName);
        secondTestData.getBuildType().setProject(project);

        checkedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType());
        uncheckedWithSuperUser.getBuildConfigRequest().create(secondTestData.getBuildType())
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("Build configuration with name \"" + firstBuildConfigName
                        + "\" already exists in project: \"" + project.getName() + "\""));
    }

    @Test
    public void buildConfigurationCanNotBeCreatedWithoutName() {
        testData.getBuildType().setName("");

        uncheckedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType())
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("When creating a build type, non empty name should be provided."));
    }

    @Test
    public void buildConfigurationCanNotBeCreatedWithoutId() {
        testData.getBuildType().setId("");

        uncheckedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType())
                .then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body(Matchers.containsString("Build configuration or template ID must not be empty."));
    }

    @Test
    public void buildConfigurationCanNotBeCreatedWithIdStartingWithNumber() {
        testData.getBuildType().setId("1" + testData.getBuildType().getId());

        uncheckedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType())
                .then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body(Matchers.containsString("Build configuration or template ID \"" + testData.getBuildType().getId()
                        + "\" is invalid: starts with non-letter character '1'. ID should start with a latin letter and " +
                        "contain only latin letters, digits and underscores (at most 225 characters)"));
    }

    @Test
    public void buildConfigurationCanNotBeCreatedWithIdMoreThen225Symbols() {
        testData.getBuildType().setId("id_" + RandomStringUtils.randomAlphabetic(223));

        uncheckedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType())
                .then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body(Matchers.containsString("Build configuration or template ID \"" + testData.getBuildType().getId()
                        + "\" is invalid: it is 226 characters long while the maximum length is 225. ID should start " +
                        "with a latin letter and contain only latin letters, digits and underscores (at most 225 characters)"));
    }

    @Test
    public void buildConfigurationCanNotBeCreatedWithoutProject() {
        testData.getBuildType().setProject(null);

        uncheckedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType())
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("Build type creation request should contain project node."));
    }

    @Test
    public void buildConfigurationCanNotBeCreatedInNonExistingProject() {
        testData.getBuildType().getProject().setId("NonExistingProjectId");

        uncheckedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType())
                .then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("No project found by locator 'count:1,id:NonExistingProjectId'."));
    }
}
