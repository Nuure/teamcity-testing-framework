package com.example.teamcity.api;

import com.example.teamcity.api.generators.TestData;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.requests.checked.CheckedBase;
import com.example.teamcity.api.spec.Specifications;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.example.teamcity.api.enums.Endpoint.PROJECT_ENDPOINT;

public class CreateProjectPositiveTest extends BaseApiTest {

    private TestData testData;

    @BeforeMethod
    public void prepareTestData() {
        testData = testDataStorage.addTestData();
    }

    @Test
    public void projectCanBeCreatedWithIdUpTo225Symbols() {
        testData.getProject().setId("id_" + RandomStringUtils.randomAlphabetic(222));

        Project project = createCheckedProjectWithSuperUser(testData);
        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
    }

    @Test
    public void projectCanBeCreatedWithLongName() {
        testData.getProject().setName("name_" + RandomStringUtils.randomAlphabetic(1000));

        Project project = createCheckedProjectWithSuperUser(testData);
        softy.assertThat(project.getName()).isEqualTo(testData.getProject().getName());
    }

    @Test
    public void projectCanBeCreatedWithShortId() {
        testData.getProject().setId("a");

        Project project = createCheckedProjectWithSuperUser(testData);
        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
    }

    @Test
    public void projectCanBeCreatedWithShortName() {
        testData.getProject().setName("1");

        Project project = createCheckedProjectWithSuperUser(testData);
        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
    }

    @Test
    public void projectCanBeCreatedWithAsSubprojectToAnotherProject() {
        TestData secondTestData = testDataStorage.addTestData();

        Project firstProject = createCheckedProjectWithSuperUser(testData);
        secondTestData.getProject().getParentProject().setLocator(firstProject.getId());
        Project secondProject = createCheckedProjectWithSuperUser(secondTestData);

        softy.assertThat(secondProject.getParentProjectId()).isEqualTo(testData.getProject().getId());
    }

    @Test
    public void projectCanBeCreatedWithNameEqualsId() {
        testData.getProject().setName(testData.getProject().getId());

        Project project = createCheckedProjectWithSuperUser(testData);
        softy.assertThat(project.getId()).isEqualTo(project.getName());
    }

    private Project createCheckedProjectWithSuperUser(TestData testData) {
        return (Project) new CheckedBase(Specifications.getSpec().superUserSpec(), PROJECT_ENDPOINT).create(testData.getProject());
    }
}
