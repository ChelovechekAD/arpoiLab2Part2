package org.ITAcademy.part2.CatalogTests;

import org.ITAcademy.part2.utils.MockUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class MainPageTest extends CatalogTest {


    @Test(priority = 1)
    public void testShoppingCartUnauthorized() {
        driver.get("https://www.sharelane.com/cgi-bin/main.py");
        WebElement shoppingCartElem = driver.findElement(By.linkText("Shopping Cart"));
        shoppingCartElem.click();
        WebElement errorElem = driver.findElement(By.className("error_message"));
        String errorMessage = errorElem.getText();
        assertEquals(errorMessage, "Oops, error. You must log in",
                "User already authorized or something went wrong!");
    }



    @Test(priority = 2, groups = "authorized")
    public void testShoppingCartAuthorized() {
        driver.get("https://www.sharelane.com/cgi-bin/main.py");
        WebElement shoppingCartElem = driver.findElement(By.linkText("Shopping Cart"));
        shoppingCartElem.click();
        WebElement confMessageElem = driver.findElement(By.className("confirmation_message"));
        String confMessage = confMessageElem.getText();
        assertEquals(confMessage, "Cart is empty", "Cart is not empty!");
    }

    @Test(priority = 3, groups = "authorized")
    public void testProductPageRedirect() {
        driver.get("https://www.sharelane.com/cgi-bin/main.py");
        WebElement bookNameElem = driver.findElement(By.xpath("//table[@width='300'][1]/tbody/tr[3]/td/a"));
        String bookName = bookNameElem.getText();
        WebElement authorElem = driver.findElement(By.xpath("//table[@width='300'][1]/tbody/tr[2]/td/p/b"));
        String author = authorElem.getText();
        WebElement priceElem = driver.findElement(By.xpath("//table[@width='300'][1]/tbody/tr[4]/td/p"));
        String price = priceElem.getText();

        bookNameElem.click();

        WebElement bookNamePageElem = driver.findElement(By.xpath("//td/p[2]"));
        String bookNamePage = bookNamePageElem.getText();
        WebElement authorPageElem = driver.findElement(By.xpath("//td/p[1]/b"));
        String authorPage = authorPageElem.getText();
        WebElement pricePageElem = driver.findElement(By.xpath("//td/p[3]/b"));
        String pricePage = pricePageElem.getText();
        boolean priceEquals = pricePage.contains(price);
        assertEquals(List.of(bookNamePage, authorPage, priceEquals), List.of(bookName, author, true));
    }

    @Test(priority = 4, groups = "authorized")
    public void testAddToCart() throws InterruptedException {
        WebElement bookNamePageElem = driver.findElement(By.xpath("//td/p[2]"));
        String bookNamePage = bookNamePageElem.getText();
        WebElement authorPageElem = driver.findElement(By.xpath("//td/p[1]/b"));
        String authorPage = authorPageElem.getText();
        WebElement pricePageElem = driver.findElement(By.xpath("//td/p[3]/b"));
        String pricePage = pricePageElem.getText();
        WebElement addToCartBtn = driver.findElement(By.xpath("//td/p[2]/a"));
        addToCartBtn.click();
        String pricePageSplit = pricePage.split(" ")[1];


        WebElement cartBtn = driver.findElement(By.linkText("Shopping Cart"));
        cartBtn.click();

        WebElement titleElem = driver.findElement(By.xpath("//table[@width='700']/tbody/tr[2]/td[2]"));
        String title = titleElem.getText();
        System.out.println(title);
        WebElement authorElem = driver.findElement(By.xpath("//table[@width='700']/tbody/tr[2]/td[1]"));
        String author = authorElem.getText();
        System.out.println(authorElem.getTagName() + " " + authorElem);

        WebElement priceElem = driver.findElement(By.xpath("//table[@width='700']/tbody/tr[2]/td[4]"));
        String price = priceElem.getText() + "$";
        System.out.println(price);

        Thread.sleep(10000);

        assertEquals(List.of(bookNamePage, authorPage, pricePageSplit), List.of(title, author, price), "Wrong item in cart!");

    }

    @BeforeGroups("authorized")
    public void login() {
        driver.get("https://www.sharelane.com/cgi-bin/main.py");
        MockUtils.login();
    }
    @AfterGroups("authorized")
    public void logout() {
        driver.get("https://www.sharelane.com/cgi-bin/main.py");
        MockUtils.logout();
    }

}
