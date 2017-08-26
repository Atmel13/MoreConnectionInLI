package Locators;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by Atmel on 20.08.2017.
 */
public class loginPage {

    private WebDriver driver;

//    @FindBy(how = How.XPATH, using = "//*[@id = 'login-email']")
    @FindBy (id = "login-email")
    public WebElement emailField;

    @FindBy(how = How.XPATH, using = "//*[@id = 'login-password']")
    public WebElement passwordField;

    @FindBy(how = How.XPATH, using = "//*[@id = 'login-submit']")
    public WebElement submitButton;

    public loginPage (WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

}
