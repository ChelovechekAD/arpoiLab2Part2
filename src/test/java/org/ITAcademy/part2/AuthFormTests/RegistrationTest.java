package org.ITAcademy.part2.AuthFormTests;

import org.ITAcademy.part2.utils.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class RegistrationTest extends AuthTest {

    @Test(priority = 1)
    public void testZipCodeFormNegative() throws InterruptedException {
        driver.get("https://www.sharelane.com/cgi-bin/main.py");
        driver.findElement(By.linkText("Sign up")).click();
        WebElement inputField = driver.findElement(By.xpath("//input[@name='zip_code']"));
        inputField.sendKeys("12");
        WebElement confirmBtn = driver.findElement(By.xpath("//input[@value='Continue']"));
        confirmBtn.click();
        String errorMessage = driver.findElement(By.className("error_message")).getText();
        assertEquals(errorMessage,
                "Oops, error on page. ZIP code should have 5 digits",
                "No error message for wrong ZIP code");
    }

    @Test(priority = 2)
    public void testZipCodeFormPositive() {
        driver.get("https://www.sharelane.com/cgi-bin/main.py");
        driver.findElement(By.linkText("Sign up")).click();
        WebElement inputField = driver.findElement(By.xpath("//input[@name='zip_code']"));
        inputField.sendKeys("12345");
        WebElement confirmBtn = driver.findElement(By.xpath("//input[@value='Continue']"));
        confirmBtn.click();
        try {
            driver.findElement(By.xpath("//input[@name='first_name']"));
        } catch (NoSuchElementException e) {
            Assert.fail("No such element found! Something went wrong!");
        }
    }

    @DataProvider(name = "wrongRegDataProvider")
    public Object[][] wrongRegFormData() {
        return new Object[][]{
                {"", "", "", "", ""},
                {"Name", "Name", "", "Name123", "Name123"},
                {"", "Name", "email@test.com", "Name", "Name"},
                {"Name", "Name", "email2@test.com", "1", "1"},
                {"Name", "Name", "email2@test.com", "1", ""},
                {"Name", "Name", "email2@test.com", "1123", "1323"}
        };
    }

    @Test(dataProvider = "wrongRegDataProvider", dependsOnMethods = "testZipCodeFormPositive", priority = 3)
    public void testRegFormNegative(String firstName, String lastName,
                                    String email, String password, String passwordConf) {

        WebElement inputFirstName = driver.findElement(By.xpath("//input[@name='first_name']"));
        inputFirstName.clear();
        inputFirstName.sendKeys(firstName);
        WebElement inputLastName = driver.findElement(By.xpath("//input[@name='last_name']"));
        inputLastName.clear();
        inputLastName.sendKeys(lastName);
        WebElement inputEmail = driver.findElement(By.xpath("//input[@name='email']"));
        inputEmail.clear();
        inputEmail.sendKeys(email);
        WebElement inputPassword = driver.findElement(By.xpath("//input[@name='password1']"));
        inputPassword.clear();
        inputPassword.sendKeys(password);
        WebElement inputConfPassword = driver.findElement(By.xpath("//input[@name='password2']"));
        inputConfPassword.clear();
        inputConfPassword.sendKeys(passwordConf);

        WebElement regBtn = driver.findElement(By.xpath("//input[@value='Register']"));
        regBtn.click();
        try {
            String errorMessage = driver.findElement(By.className("error_message")).getText();
            assertEquals(errorMessage, "Oops, error on page. Some of your fields have invalid data or email was previously used");
        } catch (NoSuchElementException e) {
            Assert.fail(String.format(Constants.testRegFormNegativeErrorMessageTemplate, firstName, lastName, email, password, passwordConf));
        }
    }

    @Test(priority = 4)
    public void testRegFormPositive() {
        driver.get("https://www.sharelane.com/cgi-bin/main.py");
        driver.findElement(By.linkText("Sign up")).click();
        WebElement inputField = driver.findElement(By.xpath("//input[@name='zip_code']"));
        inputField.sendKeys("12345");
        WebElement confirmBtn = driver.findElement(By.xpath("//input[@value='Continue']"));
        confirmBtn.click();

        WebElement inputFirstName = driver.findElement(By.xpath("//input[@name='first_name']"));
        inputFirstName.clear();
        inputFirstName.sendKeys("Name");
        WebElement inputLastName = driver.findElement(By.xpath("//input[@name='last_name']"));
        inputLastName.clear();
        inputLastName.sendKeys("Name");
        WebElement inputEmail = driver.findElement(By.xpath("//input[@name='email']"));
        inputEmail.clear();
        inputEmail.sendKeys("testEmail@test.org");
        WebElement inputPassword = driver.findElement(By.xpath("//input[@name='password1']"));
        inputPassword.clear();
        inputPassword.sendKeys("1234");
        WebElement inputConfPassword = driver.findElement(By.xpath("//input[@name='password2']"));
        inputConfPassword.clear();
        inputConfPassword.sendKeys("1234");

        WebElement regBtn = driver.findElement(By.xpath("//input[@value='Register']"));
        regBtn.click();
    }
}
