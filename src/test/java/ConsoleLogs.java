import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v156.log.Log;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ConsoleLogs {
    EdgeDriver driver;

    @BeforeClass
    public void setup(){
       // WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void viewBrowserConsoleLogs(){
        //Get The DevTools & Create A Session
        DevTools devTools = driver.getDevTools();
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
