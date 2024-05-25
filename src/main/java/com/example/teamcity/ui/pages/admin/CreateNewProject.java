package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.element;

public class CreateNewProject extends CreateObjectPage {
    private final SelenideElement projectNameInput = element(Selectors.byId("projectName"));

    public CreateNewProject open(String parentProjectId) {
        openCreatePage(parentProjectId, "createProjectMenu");
        waitUntilPageIsLoaded();
        return this;
    }

    public CreateNewProject createProjectByUrl(String url) {
        urlInput.sendKeys(url);
        submit();
        return this;
    }

    public void setupProject(String projectName, String buildTypeName) {
        projectNameInput.shouldBe(Condition.visible, Duration.ofSeconds(30)).clear();
        projectNameInput.sendKeys(projectName);
        buildTypeNameInput.clear();
        buildTypeNameInput.sendKeys(buildTypeName);
        submit();
    }

    public void setupProject(String projectName, String buildTypeName, String defaultBranch) {
        projectNameInput.shouldBe(Condition.visible, Duration.ofSeconds(30)).clear();
        projectNameInput.sendKeys(projectName);
        buildTypeNameInput.clear();
        buildTypeNameInput.sendKeys(buildTypeName);
        defaultBranchInput.clear();
        defaultBranchInput.sendKeys(defaultBranch);
        submit();
    }
}
