import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.SkipException;
import utils.DriverFactory;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class ScreenshotsTests {

    WebDriver driver;

    @BeforeMethod
    public void setUp(){
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
        driver.get("https://applitools.com/");
    }

    @Test
    public void testTakeWebElementScreenshot() throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement nextGenerationPlatform = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("/html/body/section[6]/div/div/div[1]/h2")
                )
        );
        File source = nextGenerationPlatform.getScreenshotAs(OutputType.FILE);
        File destination = new File("Next Generation Platform.png");
        FileHandler.copy(source, destination);
    }

    @Test
    public void testTakeWebElementPageSectionScreenshot() throws IOException {
        WebElement applitoolsPageSection = driver.findElement(By.xpath("/html/body/section[6]/div"));
        File source = applitoolsPageSection.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(source, new File("Applitools Page Section.png"));
    }

    @Test
    public void testTakeFullPageScreenshot() throws IOException {
        if (driver instanceof FirefoxDriver) {
            File source = ((FirefoxDriver) driver)
                    .getFullPageScreenshotAs(OutputType.FILE);
            FileHandler.copy(source,
                    new File("Applitools Full Page Screenshot.png"));
        } else {
            throw new SkipException(
                    "Full-page screenshot test is supported only in Firefox");
        }
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
}
