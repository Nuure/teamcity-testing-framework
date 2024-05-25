package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.ui.pages.setup.StartUpPage;
import org.testng.annotations.Test;

public class SetupTest {
    @Test
    public void setupTeamCityServerTest() {
        new StartUpPage()
                .open()
                .setupTeamCityServer()
                .getHeader().shouldHave(Condition.text("Create Administrator Account"));
    }
}
