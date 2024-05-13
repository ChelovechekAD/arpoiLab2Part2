package org.ITAcademy.part2.utils;

import lombok.experimental.UtilityClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

@UtilityClass
public class MockUtils {
    private static WebDriver driver;

    public static WebDriver getDriver(){
        if (driver == null){
            driver = openDriver();
        }
        return driver;
    }

    public static void closeDriver(){
        driver.quit();
        driver = null;
    }

    private WebDriver openDriver() {
        ChromeOptions options = new ChromeOptions();
//            options.addArguments("--remote-debugging-port=9222", "--headless", "--no-sandbox");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        return driver;
    }

    public void login() {
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

        String email = driver.findElement(By.xpath("//td[contains(text(),'Email')]/following-sibling::td/b")).getText();
        String password = driver.findElement(By.xpath("//td[contains(text(),'Password')]/following-sibling::td")).getText();


        driver.get("https://www.sharelane.com/cgi-bin/main.py");

        WebElement loginEmailInput = driver.findElement(By.xpath("//input[@name='email']"));
        loginEmailInput.sendKeys(email);

        WebElement loginPassInput = driver.findElement(By.xpath("//input[@name='password']"));
        loginPassInput.sendKeys(password);

        WebElement loginBtn = driver.findElement(By.xpath("//input[@value='Login']"));
        loginBtn.click();
    }

    public void logout() {
        WebElement logoutElem = driver.findElement(By.linkText("Logout"));
        logoutElem.click();
    }

}
