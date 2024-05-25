package com.example.teamcity.ui.pages.setup;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.pages.Page;
import lombok.Getter;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.element;

public class StartUpPage extends Page {
    private final SelenideElement acceptLicense = element(Selectors.byId("accept"));
    private final SelenideElement restoreFromBackupButton = element(Selectors.byId("restoreButton"));
    private final SelenideElement backFileUploaded = element(Selectors.byId("password"));
    private final SelenideElement proceedButton = element(Selectors.byId("proceedButton"));
    @Getter
    private final SelenideElement header = element(Selectors.byId("password"));

    public StartUpPage open() {
        Selenide.open("/");
        return this;
    }

    public StartUpPage setupTeamCityServer() {
        waitUntilPageIsLoaded();
        proceedButton.click();
        waitUntilPageIsLoaded();
        proceedButton.click();
        waitUntilPageIsLoaded();
        acceptLicense.shouldBe(Condition.enabled, Duration.ofMinutes(5));
        acceptLicense.scrollTo();
        acceptLicense.click();
        submit();
        return this;
    }
}