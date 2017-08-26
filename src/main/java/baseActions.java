import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.net.URISyntaxException;
import java.nio.file.Paths;

/**
 * Created by Atmel on 15.06.2017.
 */
public abstract class baseActions {

    protected WebDriver driver;
    protected GeneralActions actions;
    ChromeOptions options = new ChromeOptions();

    private WebDriver getDriver(ChromeOptions options) {

        System.setProperty("webdriver.chrome.driver", getResource("/chromedriver.exe"));
                return new ChromeDriver(options);
        }

    private String getResource(String resourceName) {
        try {
            return Paths.get(baseActions.class.getResource(resourceName).toURI()).toFile().getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.out.println("WHY?????????????????????????????????");
        }
        return resourceName;
    }

    @BeforeClass
    public void setUp() {
        //options.addArguments("user-data-dir=C:\\Users\\Atmel\\AppData\\Local\\Google\\Chrome\\User Data");
        driver = getDriver(options);
        actions = new GeneralActions(driver);
    }

    @AfterClass(enabled = false)

    public void tearDown() throws InterruptedException {

        Thread.sleep(5000);
        if (driver != null) {
            driver.quit();
        }
    }


}
