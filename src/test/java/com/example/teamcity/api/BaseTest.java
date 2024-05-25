package com.example.teamcity.api;

import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.models.AuthSettings;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.UncheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static io.restassured.RestAssured.given;

public class BaseTest {
    protected SoftAssertions softy;

    public TestDataStorage testDataStorage;
    public CheckedRequests checkedWithSuperUser = new CheckedRequests(Specifications.getSpec().superUserSpec());
    public UncheckedRequests uncheckedWithSuperUser = new UncheckedRequests(Specifications.getSpec().superUserSpec());


    @BeforeMethod
    public void beforeTest() {
        softy = new SoftAssertions();
        testDataStorage = TestDataStorage.getStorage();
    }

    @AfterMethod
    public void afterTest() {
        testDataStorage.delete();
        softy.assertAll();
    }

    public static void enablePerProjectPermissions() {
        RequestSpecification spec = Specifications.getSpec().superUserSpec();
        AuthSettings authSettings = given().spec(spec)
                .get("/app/rest/server/authSettings").as(AuthSettings.class);
        if (!authSettings.isPerProjectPermissions()) {
            authSettings.setPerProjectPermissions(true);
            given().spec(spec).body(authSettings).put("/app/rest/server/authSettings");
        }
    }
}
