package com.selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

        //Open Basic Auth Link
        driver.findElement(By.xpath("//*[@id='content']/ul/li[3]/a")).click();

        //Get the current URL and insert username and password to authenticate
        String oriBasicAuthURL = driver.getCurrentUrl();
        String newBasicAuthURL = oriBasicAuthURL.replace("http://", "");
        String username = "admin";
        String password = "admin";

        String URL = "http://" + username + ":" + password + "@" + newBasicAuthURL;
        driver.navigate().to(URL);

        //returns that the authentication is successful
        String actualString = driver.findElement(By.xpath("//*[@id='content']/div/p")).getText();
        assertTrue(actualString.contains("Congratulations! You must have the proper credentials."));
        driver.quit();
    }

    @Test
    public void checkBrokenImagesReturnTrue()
    {
        WebDriver driver = new ChromeDriver();
        driver.navigate().to("http://the-internet.herokuapp.com/?ref=hackernoon.com");
        driver.manage().window().maximize();

        //Open Broken Image Link
        driver.findElement(By.xpath("//*[@id='content']/ul/li[4]/a")).click();

        //Get all the images on current webpage and run in loop to check if there is any broken image
        boolean brokeImage = false;
        for (WebElement image : driver.findElements(By.cssSelector("img")))
        {
            if (image.getAttribute("naturalWidth").equals("0"))
            {
                System.out.println(image.getAttribute("outerHTML") + " is broken.");
                brokeImage = true;
            }
        }

        //returns that there is a broken image on this webpage
        assertEquals(true, brokeImage);
        driver.quit();
    }
}
