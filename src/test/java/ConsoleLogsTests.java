import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v156.log.Log;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.DriverFactory;


public class ConsoleLogsTests {
    WebDriver driver;

    @BeforeClass
    public void setup() {
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testViewBrowserConsoleLogs() {

        if (!(driver instanceof HasDevTools)) {
            throw new SkipException(
                    "CDP is not supported by this browser"
            );
        }

        //Get The DevTools & Create A Session
        DevTools devTools =
                ((HasDevTools) driver).getDevTools();
        devTools.createSession();

        //Enable The Console Logs
        devTools.send(Log.enable());

        //Add a Listener For The Console Logs
        devTools.addListener(Log.entryAdded(), logEntry -> {
            System.out.println("----------");
            System.out.println("Level: " + logEntry.getLevel());
            System.out.println("Text: " + logEntry.getText());
            System.out.println("Broken URL: " + logEntry.getUrl());
        });
        //Load The Aut
        driver.get("https://the-internet.herokuapp.com/broken_images");
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}
