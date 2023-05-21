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
import org.openqa.selenium.support.ui.Select;

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
    public void tickAllCheckBoxesReturnTrue()
    {
        //Open Checkboxes Link
        driver.findElement(By.xpath("//*[@id='content']/ul/li[6]/a")).click();

        //If checkbox is not tick, then tick it
        WebElement checkbox1 = driver.findElement(By.xpath("//*[@id='checkboxes']/input[1]"));
        WebElement checkbox2 = driver.findElement(By.xpath("//*[@id='checkboxes']/input[2]"));

        if(checkbox1.isSelected() == false){
            checkbox1.click();
        } else if (checkbox2.isSelected() == false) {
            checkbox2.click();
        }

        //validate that all checkboxes have been tick
        assertEquals(true, checkbox1.isSelected());
        assertEquals(true, checkbox2.isSelected());
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

    @Test
    public void dropdown()
    {
        //Open Dropdown Link
        driver.findElement(By.xpath("//*[@id='content']/ul/li[11]/a")).click();

        //Find the dropdown element
        Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='dropdown']")));  

        //Select Option 1 and validate that current option is correct
        dropdown.selectByIndex(1);
        assertEquals("Option 1", dropdown.getFirstSelectedOption().getText());

        //Select Option 2 and validate that current option is correct
        dropdown.selectByIndex(2);
        assertEquals("Option 2", dropdown.getFirstSelectedOption().getText());
    }

    @Test
    public void multipleWindows()
    {
        //Open MultipleWindows Link
        driver.findElement(By.xpath("//*[@id='content']/ul/li[33]/a")).click();

        //Click on the "Click Here" link to open a new window
        driver.findElement(By.xpath("//*[@id='content']/div/a")).click();

        //Grab current number of windows opened and assert that it is the correct number 
        int currentHandleCount = driver.getWindowHandles().size();
        assertEquals(2, currentHandleCount);
    }
}
