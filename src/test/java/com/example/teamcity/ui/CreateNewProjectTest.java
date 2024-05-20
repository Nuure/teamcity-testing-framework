package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.generators.TestData;
import com.example.teamcity.api.models.Project;
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
        String projectName = testData.getProject().getName();
        String projectNameToId = projectName.substring(0, 5) + projectName.substring(5, 6).toUpperCase() + projectName.substring(6);
        testData.getProject().setId(projectNameToId.replaceAll("test_", "Test"));
    }

    @Test
    public void authorizedUserShouldBeAbleCreateNewProject() {
        new CreateNewProject()
                .open(testData.getProject().getParentProject().getLocator())
                .createProjectByUrl(GITHUB_PROJECT_URL)
                .setupProject(testData.getProject().getName(), testData.getBuildType().getName());

        checkNewProjectShownOnFavoritesProjectsPage(testData.getProject().getName());
        checkCreatedProjectParams(testData.getProject().getId(), testData.getProject().getParentProject().getLocator());
    }

    @Test
    public void authorizedUserShouldBeAbleCreateNewProjectAsSubproject() {
        TestData secondTestData = testDataStorage.addTestData();
        Project parentProject = checkedWithSuperUser.getProjectRequest().create(secondTestData.getProject());
        testData.getProject().setId(secondTestData.getProject().getId() + "_" + testData.getProject().getId());
        new CreateNewProject()
                .open(parentProject.getId())
                .createProjectByUrl(GITHUB_PROJECT_URL)
                .setupProject(testData.getProject().getName(), testData.getBuildType().getName());

        checkNewProjectShownOnFavoritesProjectsPage(testData.getProject().getName());
        checkCreatedProjectParams(testData.getProject().getId(), secondTestData.getProject().getId());
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

    private void checkCreatedProjectParams(String projectId, String parentProjectId) {
        Project project = checkedWithSuperUser.getProjectRequest().get(projectId);
        softy.assertThat(project.getName()).isEqualTo(testData.getProject().getName());
        softy.assertThat(project.getParentProjectId()).isEqualTo(parentProjectId);
        softy.assertThat(project.getId()).isEqualTo(projectId);
    }
}
