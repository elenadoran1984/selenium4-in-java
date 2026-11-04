import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v156.network.Network;
import org.openqa.selenium.devtools.v156.network.model.ConnectionType;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.DriverFactory;

import java.util.Optional;

public class NetworkConditionsTests {
    WebDriver driver;
    DevTools devTools;

    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();

        if (driver instanceof ChromeDriver) {
            devTools = ((ChromeDriver) driver).getDevTools();
        }
    }

    @Test
    public void testEnableSlowWikipedia() {

        if (!(driver instanceof ChromeDriver)) {
            throw new SkipException("DevTools network emulation is supported only in Chrome");
        }

        devTools.createSession();
        devTools.send(Network.enable(
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        ));
        devTools.send(Network.emulateNetworkConditions(
                false,                              // Offline
                150,                                // Latency (ms)
                2500,                               // Download throughput
                2000,                               // Upload throughput
                Optional.of(ConnectionType.CELLULAR3G),
                Optional.empty(),                   // Packet loss
                Optional.empty(),                   // Packet queue length
                Optional.empty()                    // Packet reordering
        ));
        driver.get("https://www.wikipedia.org/");
        System.out.println("Enable Slow Network: " + driver.getTitle());
    }

    @Test
    public void testDoNotEnableRexJonesII(){
        driver.get("https://www.rexjones2.com/");
        System.out.println("Do Not Enable Network: " + driver.getTitle());
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
}
