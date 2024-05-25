package com.example.teamcity.api;

import org.testng.annotations.BeforeSuite;

public class BaseApiTest extends BaseTest {

    @BeforeSuite
    public void enablePermissions() {
        BaseTest.enablePerProjectPermissions();
    }
 }
