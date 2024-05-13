package org.ITAcademy.part2.CatalogTests;

import org.ITAcademy.part2.utils.MockUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public abstract class CatalogTest {
    protected static WebDriver driver;
    @BeforeSuite
    static void setUp() {
        driver = MockUtils.getDriver();
    }

    @AfterSuite
    static void tearDown() {
        MockUtils.closeDriver();
    }
}