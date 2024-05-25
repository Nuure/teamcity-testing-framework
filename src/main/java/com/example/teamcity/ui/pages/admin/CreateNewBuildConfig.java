package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.element;

public class CreateNewBuildConfig extends CreateObjectPage {
    private final SelenideElement autoDetectedBuildStepCheckbox = element(Selectors.byId("runnerId"));

    public CreateNewBuildConfig open(String parentProjectId) {
        openCreatePage(parentProjectId, "createBuildTypeMenu");
        waitUntilPageIsLoaded();
        return this;
    }

    public CreateNewBuildConfig createBuildConfigByUrl(String url) {
        urlInput.sendKeys(url);
        submit();
        return this;
    }

    public CreateNewBuildConfig setupBuildConfig(String buildTypeName) {
        buildTypeNameInput.shouldBe(Condition.visible, Duration.ofSeconds(30)).clear();
        buildTypeNameInput.clear();
        buildTypeNameInput.sendKeys(buildTypeName);
        submit();
        return this;
    }

    public void setupBuildConfig(String buildTypeName, String defaultBranch) {
        buildTypeNameInput.shouldBe(Condition.visible, Duration.ofSeconds(30)).clear();
        buildTypeNameInput.clear();
        buildTypeNameInput.sendKeys(buildTypeName);
        defaultBranchInput.clear();
        defaultBranchInput.sendKeys(defaultBranch);
        submit();
    }
}
