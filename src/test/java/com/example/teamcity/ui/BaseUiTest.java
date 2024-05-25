package com.example.teamcity.ui;

import com.codeborne.selenide.Configuration;
import com.example.teamcity.api.BaseTest;
import com.example.teamcity.api.config.Config;
import com.example.teamcity.api.models.User;
import com.example.teamcity.api.requests.checked.CheckedUser;
import com.example.teamcity.api.spec.Specifications;
import com.example.teamcity.ui.enums.Errors;
import com.example.teamcity.ui.pages.LoginPage;
import org.testng.annotations.BeforeSuite;

import static com.example.teamcity.ui.pages.admin.CreateObjectPage.getErrorText;

public class BaseUiTest extends BaseTest {
    protected static final String GITHUB_PROJECT_URL = "https://github.com/AlexPshe/spring-core-for-qa";

    @BeforeSuite
    public void setupUiTests() {
        Configuration.baseUrl = "http://" + Config.getProperty("host");
        Configuration.remote = Config.getProperty("remote");
        Configuration.reportsFolder = "target/surefire-reports";
        Configuration.downloadsFolder = "target/downloads";
        BrowserSettings.setup(Config.getProperty("browser"));
    }

    public void loginAsUser(User user) {
        new CheckedUser(Specifications.getSpec().superUserSpec()).create(user);
        new LoginPage().open().login(user);
    }

    public void checkErrorText(Errors error) {
        softy.assertThat(getErrorText(error.getErrorType())).isEqualTo(error.getText());
    }

    @BeforeSuite
    public void enablePermissions() {
        BaseTest.enablePerProjectPermissions();
    }
}
