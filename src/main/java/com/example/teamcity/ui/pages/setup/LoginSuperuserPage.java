package com.example.teamcity.ui.pages.setup;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.api.config.Config;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.pages.Page;

import static com.codeborne.selenide.Selenide.element;

public class LoginSuperuserPage extends Page {
    private static final String LOGIN_SUPERUSER_URL = "/login.html?super=1";
    private final SelenideElement authTokenInput = element(Selectors.byId("password"));

    public LoginSuperuserPage open() {
        Selenide.open(LOGIN_SUPERUSER_URL);
        waitUntilPageIsLoaded();
        return this;
    }

    public void login() {
        authTokenInput.sendKeys(Config.getProperty("superUserToken"));
        submit();
    }
}
