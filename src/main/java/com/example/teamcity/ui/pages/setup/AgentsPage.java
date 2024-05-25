package com.example.teamcity.ui.pages.setup;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.selector.ByAttribute;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.pages.Page;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.element;

public class AgentsPage extends Page {
    private final SelenideElement agent = element(Selectors.byDataTest("agent"));
    private final SelenideElement toggle = element(new ByAttribute("title", "Agent is enabled, click to disable."));

    public AgentsPage open() {
        Selenide.open("/agents/overview");
        return this;
    }

    public void checkAgentShown() {
        waitUntilPageIsLoaded();
        agent.shouldBe(Condition.visible, Duration.ofSeconds(30));
        toggle.shouldBe(Condition.visible, Duration.ofSeconds(30));
    }

}
