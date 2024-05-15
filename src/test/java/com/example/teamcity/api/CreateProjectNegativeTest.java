package com.example.teamcity.api;

import com.example.teamcity.api.generators.TestData;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.requests.UncheckedRequests;
import com.example.teamcity.api.requests.checked.CheckedBase;
import com.example.teamcity.api.spec.Specifications;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.example.teamcity.api.enums.Endpoint.PROJECT_ENDPOINT;

public class CreateProjectNegativeTest extends BaseApiTest {

    private TestData testData;

    @BeforeMethod
    public void prepareTestData() {
        testData = testDataStorage.addTestData();
    }

    @Test
    public void canNotCreateTwoProjectsWithTheSameId() {
        TestData secondTestData = testDataStorage.addTestData();

        String firstProjectId = testData.getProject().getId();
        secondTestData.getProject().setId(firstProjectId);

        checkedWithSuperUser.getProjectRequest().create(testData.getProject());
        uncheckedWithSuperUser.getProjectRequest().create(secondTestData.getProject())
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("Project ID \"" + firstProjectId + "\" is already used by another project"));
    }

    @Test
    public void canNotCreateTwoProjectsWithTheSameName() {
        TestData secondTestData = testDataStorage.addTestData();

        String firstProjectName = testData.getProject().getName();
        secondTestData.getProject().setName(firstProjectName);

        checkedWithSuperUser.getProjectRequest().create(testData.getProject());
        uncheckedWithSuperUser.getProjectRequest().create(secondTestData.getProject())
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("Project with this name already exists: " + firstProjectName));
    }

    @Test
    public void projectCanNotBeCreatedWithoutName() {
        testData.getProject().setName("");

        uncheckedWithSuperUser.getProjectRequest().create(testData.getProject())
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("Project name cannot be empty"));
    }

    @Test
    public void projectCanNotBeCreatedWithoutId() {
        testData.getProject().setId("");

        uncheckedWithSuperUser.getProjectRequest().create(testData.getProject())
                .then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body(Matchers.containsString("Project ID must not be empty"));
    }

    @Test
    public void projectCanNotBeCreatedWithIdMoreThen225Symbols() {
        testData.getProject().setId("id_" + RandomStringUtils.randomAlphabetic(223));

        uncheckedWithSuperUser.getProjectRequest().create(testData.getProject())
                .then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body(Matchers.containsString("Project ID \"" + testData.getProject().getId()
                        + "\" is invalid: it is 226 characters long while the maximum length is 225. ID should start " +
                        "with a latin letter and contain only latin letters, digits and underscores (at most 225 characters)"));
    }

    @Test
    public void projectCanNotBeCreatedWithIdStartingWithNumber() {
        testData.getProject().setId("1" + testData.getProject().getId());

        uncheckedWithSuperUser.getProjectRequest().create(testData.getProject())
                .then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body(Matchers.containsString("Project ID \"" + testData.getProject().getId()
                        + "\" is invalid: starts with non-letter character '1'. ID should start with a latin letter and " +
                        "contain only latin letters, digits and underscores (at most 225 characters)"));
    }

    @Test
    public void projectCanNotBeCreatedWithoutLocator() {
        testData.getProject().getParentProject().setLocator("");

        uncheckedWithSuperUser.getProjectRequest().create(testData.getProject())
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("No project specified. Either 'id', 'internalId' or 'locator' attribute should be present."));
    }

    @Test
    public void projectCanNotBeCreatedWithNonExistingLocator() {
        testData.getProject().getParentProject().setLocator("nonExistingProjectId");

        uncheckedWithSuperUser.getProjectRequest().create(testData.getProject())
                .then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("No project found by name or internal/external id 'nonExistingProjectId'."));
    }

    private Response createUncheckedProjectWithSuperUser(TestData testData) {
        return (Response) new UncheckedRequests(Specifications.getSpec().superUserSpec(), PROJECT_ENDPOINT).create(testData.getProject());
    }
}
