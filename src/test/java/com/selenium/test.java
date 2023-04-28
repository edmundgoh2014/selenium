package com.selenium;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Unit test for simple App.
 */
public class test 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void basicAuthPassed()
    {
        WebDriver driver = new ChromeDriver();
        driver.navigate().to("http://the-internet.herokuapp.com/?ref=hackernoon.com");
        driver.manage().window().maximize();


        driver.findElement(By.xpath("//*[@id='content']/ul/li[3]/a")).click();

        String oriBasicAuthURL = driver.getCurrentUrl();
        String newBasicAuthURL = oriBasicAuthURL.replace("http://", "");
        String username = "admin";
        String password = "admin";

        String URL = "http://" + username + ":" + password + "@" + newBasicAuthURL;
        driver.navigate().to(URL);

        String actualString = driver.findElement(By.xpath("//*[@id='content']/div/p")).getText();
        assertTrue(actualString.contains("Congratulations! You must have the proper credentials."));
        driver.quit();
    }
}
