package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.pages.Page;

import static com.codeborne.selenide.Selenide.element;

public class CreateObjectPage extends Page {
    protected final SelenideElement urlInput = element(Selectors.byId("url"));
    protected final SelenideElement buildTypeNameInput = element(Selectors.byId("buildTypeName"));
    protected final SelenideElement defaultBranchInput = element(Selectors.byId("branch"));

    public CreateObjectPage openCreatePage(String parentProjectId, String showMode) {
        Selenide.open("/admin/createObjectMenu.html?projectId=" + parentProjectId + "&showMode=" + showMode);
        waitUntilPageIsLoaded();
        return this;
    }

    public static String getErrorText(String errorType) {
        return element(Selectors.byId(errorType)).getText();
    }

}
