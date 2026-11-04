import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.DriverFactory;

import java.util.Iterator;
import java.util.Set;

public class WindowAndTabManagementTests {

    WebDriver driver;

    @BeforeMethod
    public void setUp(){
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
        driver.get("https://www.ivet.ro/");
        System.out.println("Title: " + driver.getTitle());
    }

    @Test
    public void testNewWindowTab() throws InterruptedException {
        //WebDriver newWindow = driver.switchTo().newWindow(WindowType.TAB);
        WebDriver newWindow = driver.switchTo().newWindow(WindowType.WINDOW);
        newWindow.get("https://www.ivet.ro/ro/login.html");
        System.out.println("Title: " + driver.getTitle());
        Thread.sleep(10000);
    }

    @Test
    public void testWorkingInBothWindowTab(){
        //Automatically Open & Switch To The New Window Or Tab
        driver.switchTo().newWindow(WindowType.TAB).get("https://www.ivet.ro/ro/login.html");
        System.out.println("Title: " + driver.getTitle());

        //Work In The New Window Or Tab
        driver.findElement(By.id("Email")).sendKeys("email");
        driver.findElement(By.id("Password")).sendKeys("password");
        driver.findElement(By.id("loginButton")).click();

        //Get The Window ID Handles
        Set<String> allWindowTabs = driver.getWindowHandles();
        Iterator<String> iterate = allWindowTabs.iterator();
        String mainFirstWindow= iterate.next();

        //Switch & Work In The Main Window Or Tab
        driver.switchTo().window(mainFirstWindow);
        //driver.manage().window().minimize();
        driver.findElement(By.id("sbox")).sendKeys("geaca");
        driver.findElement(By.className("headerSearchButton")).click();
        System.out.println("Title: " + driver.getTitle());
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
}
