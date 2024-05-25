package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.ui.pages.setup.AgentsPage;
import com.example.teamcity.ui.pages.setup.AgentsUnauthPage;
import com.example.teamcity.ui.pages.setup.LoginSuperuserPage;
import com.example.teamcity.ui.pages.setup.StartUpPage;
import org.testng.annotations.Test;

public class SetupTest extends BaseUiTest {
    @Test
    public void setupTeamCityServerTest() {
        new StartUpPage()
                .open()
                .setupTeamCityServer()
                .getHeader().shouldHave(Condition.text("Create Administrator Account"));
    }

    @Test
    public void setupTeamCityAgentTest() {
        new LoginSuperuserPage()
                .open()
                .login();
        new AgentsUnauthPage()
                .open()
                .authorizeAgent();
        new AgentsPage()
                .checkAgentShown();
    }
}
