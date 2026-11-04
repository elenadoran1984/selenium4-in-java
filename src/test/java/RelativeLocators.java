import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

import static org.openqa.selenium.support.locators.RelativeLocator.with;

public class RelativeLocators {
    WebDriver driver;

    @BeforeMethod
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    @Test
    public void testRelativeLocator() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement forgotYourPassword1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.orangehrm-login-slot > div.orangehrm-login-form > form > div.orangehrm-login-forgot")));
        WebElement loginButton1 = driver.findElement(RelativeLocator.with(
                By.cssSelector("div.orangehrm-login-slot > div.orangehrm-login-form > form > div.oxd-form-actions.orangehrm-login-action > button")
        ).above(forgotYourPassword1));
        System.out.println("Login button is: " + loginButton1.getText());

        WebElement loginButton2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.orangehrm-login-slot > div.orangehrm-login-form > form > div.oxd-form-actions.orangehrm-login-action > button")));
        WebElement forgotYourPassword2 = driver.findElement(RelativeLocator.with(
                By.tagName("p")
        ).below(loginButton2));
        System.out.println("Forget your password is: " + forgotYourPassword2.getText());

        WebElement loginButton3 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.orangehrm-login-slot > div.orangehrm-login-form > form > div.oxd-form-actions.orangehrm-login-action > button")));
        List<WebElement> credentials = driver.findElements(RelativeLocator.with(
                By.tagName("p")
        ).above(loginButton3));
        System.out.println("Credentials are: " + credentials.size());
        for (WebElement credential :credentials) {
            System.out.println(credential.getText());
        }
        }

    @Test
    public void testListOfElements(){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement footer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.orangehrm-login-slot > div.orangehrm-login-footer")));

        List<WebElement> allsocialMedia = driver.findElements(with(
                By.tagName("a")
        ).below(footer));
        System.out.println("Social Media are: " + allsocialMedia.size());
        Collections.reverse(allsocialMedia);
        for (WebElement socialMedia : allsocialMedia){
            System.out.println(socialMedia.getAttribute("href"));
        }
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
    }