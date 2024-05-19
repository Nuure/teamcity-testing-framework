package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.generators.TestData;
import com.example.teamcity.ui.pages.admin.CreateNewProject;
import com.example.teamcity.ui.pages.favorites.ProjectsPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.example.teamcity.ui.enums.Errors.ERROR_BRANCH;
import static com.example.teamcity.ui.enums.Errors.ERROR_BUILD_NAME;
import static com.example.teamcity.ui.enums.Errors.ERROR_PROJECT_NAME;
import static com.example.teamcity.ui.enums.Errors.ERROR_URL;

public class CreateNewProjectTest extends BaseUiTest {
    private TestData testData;

    @BeforeMethod
    private void createUserAndLogin() {
        testData = testDataStorage.addTestData();
        loginAsUser(testData.getUser());
    }

    @Test
    public void authorizedUserShouldBeAbleCreateNewProject() {
        new CreateNewProject()
                .open(testData.getProject().getParentProject().getLocator())
                .createProjectByUrl(GITHUB_PROJECT_URL)
                .setupProject(testData.getProject().getName(), testData.getBuildType().getName());

        checkNewProjectShownOnFavoritesProjectsPage(testData.getProject().getName());
    }

    @Test
    public void authorizedUserShouldBeAbleCreateNewProjectAsSubproject() {
        String firstProjectId = checkedWithSuperUser.getProjectRequest().create(testData.getProject()).getId();
        new CreateNewProject()
                .open(firstProjectId)
                .createProjectByUrl(GITHUB_PROJECT_URL)
                .setupProject(testData.getProject().getName(), testData.getBuildType().getName());

        checkNewProjectShownOnFavoritesProjectsPage(testData.getProject().getName());
    }

    @Test
    public void emptyRequiredFieldsCauseErrorsOnProjectCreation() {
        CreateNewProject newProject = new CreateNewProject()
                .open(testData.getProject().getParentProject().getLocator())
                .createProjectByUrl("");
        checkErrorText(ERROR_URL);
        newProject
                .createProjectByUrl(GITHUB_PROJECT_URL)
                .setupProject("", "", "");
        checkErrorText(ERROR_PROJECT_NAME);
        checkErrorText(ERROR_BUILD_NAME);
        checkErrorText(ERROR_BRANCH);
    }

    private void checkNewProjectShownOnFavoritesProjectsPage(String projectName) {
        new ProjectsPage().open()
                .getSubprojects()
                .stream().reduce((first, second) -> second).get()
                .getHeader().shouldHave(Condition.text(projectName));
    }
}
