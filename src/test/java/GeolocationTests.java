import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v156.emulation.Emulation;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.DriverFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GeolocationTests {

    WebDriver driver;

    @BeforeMethod
    public void setUp(){
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testMockGeoLocation_executeCDPCommand() {

        if (!(driver instanceof ChromeDriver)) {
            throw new SkipException("CDP is supported only in Chrome");
        }

        Map<String, Object> coordinates = new HashMap<>();
        coordinates.put("latitude", 32.746940);
        coordinates.put("longitude", -97.092400);
        coordinates.put("accuracy", 1);

        ((ChromeDriver) driver).executeCdpCommand(
                "Emulation.setGeolocationOverride", coordinates);

        driver.get("https://where-am-i.org/");
    }

    @Test
    public void testMockGeoLocation_DevTools() {

        if (!(driver instanceof ChromeDriver)) {
            throw new SkipException("DevTools is supported only in Chrome");
        }

        DevTools devTools = ((ChromeDriver) driver).getDevTools();
        devTools.createSession();

        devTools.send(Emulation.setGeolocationOverride(
                Optional.of(52.5043),
                Optional.of(13.4501),
                Optional.of(1.0),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        ));

        driver.get("https://my-location.org/");
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
}
