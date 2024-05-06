package com.example.teamcity.api;

import com.example.teamcity.api.generators.TestData;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.example.teamcity.api.generators.TestDataGenerator.generateBuildTypeWithSteps;

public class BuildConfigurationPositiveTest extends BaseApiTest {

    private TestData testData;
    private Project project;

    @BeforeMethod
    public void prepareTestData() {
        testData = testDataStorage.addTestData();
        project = checkedWithSuperUser.getProjectRequest().create(testData.getProject());
    }

    @Test
    public void buildConfigurationCanBeCreatedWithIdUpTo225Symbols() {
        testData.getBuildType().setId("id_" + RandomStringUtils.randomAlphabetic(222));

        BuildType buildType = checkedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType());
        softy.assertThat(buildType.getId()).isEqualTo(testData.getBuildType().getId());
    }

    @Test
    public void buildConfigurationCanBeCreatedWithLongName() {
        testData.getBuildType().setName("name_" + RandomStringUtils.randomAlphabetic(1000));

        BuildType buildType = checkedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType());
        softy.assertThat(buildType.getName()).isEqualTo(testData.getBuildType().getName());
    }

    @Test
    public void buildConfigurationCanBeCreatedWithShortId() {
        testData.getBuildType().setId("a");

        BuildType buildType = checkedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType());
        softy.assertThat(buildType.getId()).isEqualTo(testData.getBuildType().getId());
    }

    @Test
    public void buildConfigurationCanBeCreatedWithShortName() {
        testData.getBuildType().setName("1");

        BuildType buildType = checkedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType());
        softy.assertThat(buildType.getName()).isEqualTo(testData.getBuildType().getName());
    }

    @Test
    public void twoBuildConfigurationsCanBeCreatedInOneProject() {
        TestData secondTestData = testDataStorage.addTestData();
        secondTestData.getBuildType().setProject(project);

        BuildType firstBuildType = checkedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType());
        BuildType secondBuildType = checkedWithSuperUser.getBuildConfigRequest().create(secondTestData.getBuildType());
        softy.assertThat(firstBuildType.getProject().getId()).isEqualTo(secondBuildType.getProject().getId());
    }

    @Test
    public void buildConfigurationCanBeCreatedWithNameEqualsId() {
        testData.getBuildType().setName(testData.getBuildType().getId());

        BuildType buildType = checkedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType());
        softy.assertThat(buildType.getId()).isEqualTo(buildType.getName());
    }

    @Test
    public void twoBuildConfigurationsWithTheSameNameCanBeCreatedInDifferentProjects() {
        TestData secondTestData = testDataStorage.addTestData();
        secondTestData.getBuildType().setName(testData.getBuildType().getName());

        BuildType firstBuildType = checkedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType());
        checkedWithSuperUser.getProjectRequest().create(secondTestData.getProject());

        BuildType secondBuildType = checkedWithSuperUser.getBuildConfigRequest().create(secondTestData.getBuildType());
        softy.assertThat(firstBuildType.getName()).isEqualTo(secondBuildType.getName());
    }

    @Test
    public void buildConfigurationWithStepsCanBeCreated() {
        testData.setBuildType(generateBuildTypeWithSteps(project));

        BuildType buildType = checkedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType());
        softy.assertThat(buildType.getSteps().getStep().get(0).getName()).isEqualTo(testData.getBuildType().getSteps().getStep().get(0).getName());
    }
}
