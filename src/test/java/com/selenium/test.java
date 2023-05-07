package com.selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
    private WebDriver driver;

    @Before
    public void setUp() {
        // This code will be executed before each test case
        driver = new ChromeDriver();
        driver.navigate().to("http://the-internet.herokuapp.com/?ref=hackernoon.com");
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        // This code will be executed after each test case
        driver.quit();
    }
    
    @Test
    public void basicAuthPassed()
    {
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
    }

    @Test
    public void checkBrokenImagesReturnTrue()
    {
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
    }

    @Test
    public void dragAndDrop()
    {
        //Open drag & drop link
        driver.findElement(By.xpath("//*[@id='content']/ul/li[10]/a")).click();

        WebElement columnA = driver.findElement(By.id("column-a"));
        WebElement columnB = driver.findElement(By.id("column-b"));

        //using JavascriptExecutor method to perform drag and drop function because WebDriver drag and drop method is not working
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("function createEvent(typeOfEvent) {\n" + "var event =document.createEvent(\"CustomEvent\");\n"
                    + "event.initCustomEvent(typeOfEvent,true, true, null);\n" + "event.dataTransfer = {\n" + "data: {},\n"
                    + "setData: function (key, value) {\n" + "this.data[key] = value;\n" + "},\n"
                    + "getData: function (key) {\n" + "return this.data[key];\n" + "}\n" + "};\n" + "return event;\n"
                    + "}\n" + "\n" + "function dispatchEvent(element, event,transferData) {\n"
                    + "if (transferData !== undefined) {\n" + "event.dataTransfer = transferData;\n" + "}\n"
                    + "if (element.dispatchEvent) {\n" + "element.dispatchEvent(event);\n"
                    + "} else if (element.fireEvent) {\n" + "element.fireEvent(\"on\" + event.type, event);\n" + "}\n"
                    + "}\n" + "\n" + "function simulateHTML5DragAndDrop(element, destination) {\n"
                    + "var dragStartEvent =createEvent('dragstart');\n" + "dispatchEvent(element, dragStartEvent);\n"
                    + "var dropEvent = createEvent('drop');\n"
                    + "dispatchEvent(destination, dropEvent,dragStartEvent.dataTransfer);\n"
                    + "var dragEndEvent = createEvent('dragend');\n"
                    + "dispatchEvent(element, dragEndEvent,dropEvent.dataTransfer);\n" + "}\n" + "\n"
                    + "var source = arguments[0];\n" + "var destination = arguments[1];\n"
                    + "simulateHTML5DragAndDrop(source,destination);", columnA, columnB);

        // Verify that the drop was successful
        String columnA_actualText = columnA.getText();
        String columnA_expectedText = "B";
        if (columnA_actualText.equals(columnA_expectedText)) {
            System.out.println("Drag and drop successful!");
        } else {
            System.out.println("Drag and drop failed.");
        }
    }
}
