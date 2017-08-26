package Locators;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Created by Atmel on 20.08.2017.
 */
public class peopleList {

    private WebDriver driver;



    @FindBy(how = How.ID, using = "mynetwork-tab-icon")
    public WebElement myNetworkButton;

    @FindBy(how = How.CLASS_NAME, using = "mn-pymk-list__cards")
    public WebElement listOfPeople;

    public WebElement neededPerson (int number) {
        WebElement person = driver.findElement(By.xpath("//li[contains(@class, 'list__card')][" + number + "]//span[contains(@class, 'info__occupation')]"));
        return person;
    }

    public List<WebElement> ifPersonexists (int number) {
        List<WebElement> ifPerson = driver.findElements(By.xpath("//li[contains(@class, 'list__card')][" + number + "]"));
        return ifPerson;

    }

    public WebElement profileUrl (int number) {
        WebElement profileUrl = driver.findElement(By.xpath("//li[contains(@class, 'list__card')][" + number + "]//a[contains(@class, 'pt3')]"));
        return profileUrl;
    }

    public WebElement connectButton (int number) {
        WebElement inviteButton = driver.findElement(By.xpath("//li[contains(@class, 'list__card')][" + number + "]//button[contains(@class, 'button')]"));
        return inviteButton;
    }

    public WebElement personName (int number) {
        WebElement personName = driver.findElement(By.xpath("//li[contains(@class, 'list__card')][" + number + "]//span[contains(@class, 'info__name')]"));
        return personName;
    }

    public List<WebElement> howManyPeopleOnThePage () {
        List<WebElement> howManyPeopleOnThePage = driver.findElements(By.xpath("//li[contains(@class, 'list__card')]"));
        return howManyPeopleOnThePage;

    }






    public peopleList (WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }


}
