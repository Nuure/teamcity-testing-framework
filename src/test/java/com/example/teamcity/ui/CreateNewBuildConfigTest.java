package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.generators.TestData;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.ui.pages.Project;
import com.example.teamcity.ui.pages.admin.CreateNewBuildConfig;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.example.teamcity.ui.enums.Errors.ERROR_BRANCH;
import static com.example.teamcity.ui.enums.Errors.ERROR_BUILD_NAME;
import static com.example.teamcity.ui.enums.Errors.ERROR_URL;

public class CreateNewBuildConfigTest extends BaseUiTest {

    private TestData testData;
    private String firstProjectId;
    private String buildTypeName;
    private String buildId;

    @BeforeMethod
    private void createUserAndLogin() {
        testData = testDataStorage.addTestData();
        buildTypeName = testData.getBuildType().getName();
        testData.getBuildType().setId(buildTypeName);
        firstProjectId = checkedWithSuperUser.getProjectRequest().create(testData.getProject()).getId();
        loginAsUser(testData.getUser());
        String buildNameToId = buildTypeName.substring(0, 5) + buildTypeName.substring(5, 6).toUpperCase() + buildTypeName.substring(6);
        buildId = testData.getProject().getId() + "_" + buildNameToId.replaceAll("test_", "Test");
        testData.getBuildType().setId(buildId);
    }

    @Test
    public void authorizedUserShouldBeAbleCreateNewBuildConfig() {
        new CreateNewBuildConfig()
                .open(firstProjectId)
                .createBuildConfigByUrl(GITHUB_PROJECT_URL)
                .setupBuildConfig(buildTypeName);

        checkCreatedBuildConfigParams();

        new Project()
                .open(firstProjectId)
                .getBuilds()
                .stream().reduce((first, second) -> second).get()
                .getHeader().shouldHave(Condition.text(buildTypeName));
    }

    @Test
    public void emptyRequiredFieldsCauseErrorsOnBuildConfigCreation() {
        CreateNewBuildConfig newBuildConfig = new CreateNewBuildConfig()
                .open(firstProjectId)
                .createBuildConfigByUrl("");
        checkErrorText(ERROR_URL);
        newBuildConfig
                .createBuildConfigByUrl(GITHUB_PROJECT_URL)
                .setupBuildConfig("", "");
        checkErrorText(ERROR_BUILD_NAME);
        checkErrorText(ERROR_BRANCH);
    }

    private void checkCreatedBuildConfigParams() {
        BuildType build = checkedWithSuperUser.getBuildConfigRequest().get(buildId);
        softy.assertThat(build.getName()).isEqualTo(testData.getBuildType().getName());
        softy.assertThat(build.getId()).isEqualTo(buildId);
    }
}
