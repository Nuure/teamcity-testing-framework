package com.example.teamcity.ui.pages.setup;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.selector.ByAttribute;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.pages.Page;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.element;

public class AgentsUnauthPage extends Page {
    private final SelenideElement authButton = element(new ByAttribute("data-test-authorize-agent", "true"));
    private final SelenideElement authPopup = element(Selectors.byDataTest("ring-popup"));


    public AgentsUnauthPage open() {
        Selenide.open("/agents/unauthorized");
        return this;
    }

    public AgentsUnauthPage authorizeAgent() {
        waitUntilPageIsLoaded();
        authButton.click();
        authPopup.shouldBe(Condition.visible, Duration.ofSeconds(30));
        submit();
        return this;
    }
}
