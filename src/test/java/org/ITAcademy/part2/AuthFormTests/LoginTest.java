package org.ITAcademy.part2.AuthFormTests;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LoginTest extends AuthTest {
    private String email;
    private String password;

    @BeforeGroups("loginTests")
    public void setUpForLogin(){
        driver.get("https://www.sharelane.com/cgi-bin/main.py");
        driver.findElement(By.linkText("Sign up")).click();
        WebElement inputField = driver.findElement(By.xpath("//input[@name='zip_code']"));
        inputField.sendKeys("12345");
        WebElement confirmBtn = driver.findElement(By.xpath("//input[@value='Continue']"));
        confirmBtn.click();

        WebElement inputFirstName = driver.findElement(By.xpath("//input[@name='first_name']"));
        inputFirstName.sendKeys("User");
        WebElement inputEmail = driver.findElement(By.xpath("//input[@name='email']"));
        inputEmail.sendKeys("email@test.org");
        WebElement inputPassword = driver.findElement(By.xpath("//input[@name='password1']"));
        inputPassword.sendKeys("1234");
        WebElement inputConfPassword = driver.findElement(By.xpath("//input[@name='password2']"));
        inputConfPassword.sendKeys("1234");

        WebElement regBtn = driver.findElement(By.xpath("//input[@value='Register']"));
        regBtn.click();

        email = driver.findElement(By.xpath("//td[contains(text(),'Email')]/following-sibling::td/b")).getText();
        password = driver.findElement(By.xpath("//td[contains(text(),'Password')]/following-sibling::td")).getText();
    }

    @DataProvider(name = "wrongLoginDataProvider")
    public Object[][] wrongLoginData() {
        return new Object[][]{
                {"", ""},
                {"Test", ""},
                {"Test", "Test"},
                {"", "Test"}
        };
    }

    @Test(dataProvider = "wrongLoginDataProvider", priority = 1, groups = "loginTests")
    public void testLoginNegative(String email, String password) {
        driver.get("https://www.sharelane.com/cgi-bin/main.py");

        WebElement loginEmailInput = driver.findElement(By.xpath("//input[@name='email']"));
        loginEmailInput.sendKeys(email);

        WebElement loginPassInput = driver.findElement(By.xpath("//input[@name='password']"));
        loginPassInput.sendKeys(password);

        WebElement loginBtn = driver.findElement(By.xpath("//input[@value='Login']"));
        loginBtn.click();

        WebElement errorMessageElem = driver.findElement(By.className("error_message"));
        String message = errorMessageElem.getText();

        assertEquals(message, "Oops, error. Email and/or password don't match our records",
                "Wrong error message!");
    }

    @Test(groups = "loginTests", priority = 2)
    public void testLoginPositive() {
        driver.get("https://www.sharelane.com/cgi-bin/main.py");

        WebElement loginEmailInput = driver.findElement(By.xpath("//input[@name='email']"));
        loginEmailInput.sendKeys(email);

        WebElement loginPassInput = driver.findElement(By.xpath("//input[@name='password']"));
        loginPassInput.sendKeys(password);

        WebElement loginBtn = driver.findElement(By.xpath("//input[@value='Login']"));
        loginBtn.click();

        try {
            WebElement userSpanElem = driver.findElement(By.className("user"));
            boolean correctText = userSpanElem.getText().contains("Hello");
            assertTrue(correctText);
        } catch (NoSuchElementException e) {
            Assert.fail("Something went wrong! Login unsuccessful!");
        }


    }

    @Test(dependsOnMethods = "testLoginPositive", priority = 3, groups = "loginTests")
    public void testLogout() {
        WebElement logoutElem = driver.findElement(By.linkText("Logout"));
        logoutElem.click();
        WebElement confSpanElem = driver.findElement(By.className("confirmation_message"));
        String confMessage = confSpanElem.getText();
        assertEquals(confMessage, "You've been logged out");
    }
}
