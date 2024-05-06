package com.example.teamcity.api.generators;

import java.util.ArrayList;
import java.util.List;

public class TestDataStorage {
    public static TestDataStorage testDataStorage;
    public List<TestData> testDataList;

    private TestDataStorage() {
        this.testDataList = new ArrayList<>();
    }

    public static TestDataStorage getStorage() {
        if (testDataStorage == null) {
            testDataStorage = new TestDataStorage();
        }
        return testDataStorage;
    }

    public void delete() {
        testDataList.forEach(TestData::delete);
    }

    public TestData addTestData() {
        TestData testData = TestDataGenerator.generate();
        addTestData(testData);
        return testData;
    }

    public TestData addTestData(TestData testData) {
        getStorage().testDataList.add(testData);
        return testData;
    }
}
