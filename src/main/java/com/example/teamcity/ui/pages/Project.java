package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.elements.BuildElement;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Selenide.elements;

public class Project extends Page {
    private static final String PROJECT_URL = "/project/";
    private final ElementsCollection buildsTab = elements(Selectors.byDataTest("ring-tab"));

    public Project open(String projectId) {
        Selenide.open(PROJECT_URL + projectId);
        waitUntilPageIsLoaded();
        return this;
    }

    public List<BuildElement> getBuilds() {
        buildsTab.get(0).shouldBe(Condition.visible, Duration.ofSeconds(30));
        return generatePageElements(buildsTab, BuildElement::new);
    }
}
