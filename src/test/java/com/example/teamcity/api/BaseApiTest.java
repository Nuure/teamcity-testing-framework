package com.example.teamcity.api;

import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.models.AuthSettings;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.UncheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import static io.restassured.RestAssured.given;

public class BaseApiTest extends BaseTest {
    public TestDataStorage testDataStorage;
    public CheckedRequests checkedWithSuperUser = new CheckedRequests(Specifications.getSpec().superUserSpec());
    public UncheckedRequests uncheckedWithSuperUser = new UncheckedRequests(Specifications.getSpec().superUserSpec());

    @BeforeMethod
    public void setupTest() {
        testDataStorage = TestDataStorage.getStorage();
    }

    @AfterMethod
    public void cleanTest() {
        testDataStorage.delete();
    }

    @BeforeSuite
    public void enablePerProjectPermissions() {
        RequestSpecification spec = Specifications.getSpec().superUserSpec();
        AuthSettings authSettings = given().spec(spec)
                .get("/app/rest/server/authSettings").as(AuthSettings.class);
        if (!authSettings.isPerProjectPermissions()) {
            authSettings.setPerProjectPermissions(true);
            given().spec(spec).body(authSettings).put("/app/rest/server/authSettings");
        }
    }
}
