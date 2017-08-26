package Locators;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Created by Atmel on 25.08.2017.
 */
public class profilePage {

    private WebDriver driver;

    public profilePage (WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(how = How.XPATH, using = "//button[contains(@class, 'connect primary')]")
    public WebElement connectButton;

    public List<WebElement> ifAlreadyInvited () {
        List<WebElement> ifPersonInvited = driver.findElements(By.xpath("//button[contains(@class, 'connect primary')]"));
        return ifPersonInvited;
    }

   // @FindBy(how = How.XPATH, using = "//img[contains(@class, 'pv-top-card-section__image')]")
   // public WebElement profileImage;

    By profileImage = By.xpath("//img[contains(@class, 'pv-top-card-section__image')]");
    public By getProfileImage() {return profileImage; }

    @FindBy(how = How.XPATH, using = "//button[contains(@class, 'button-secondary-large')]")
    public WebElement addANoteButton;

    @FindBy(how = How.XPATH, using = "//button[contains(@class, 'button-primary-large ml3')]")
    public WebElement sendNowButton;









}
