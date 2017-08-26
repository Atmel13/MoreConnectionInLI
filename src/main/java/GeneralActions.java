import Locators.loginPage;
import Locators.peopleList;
import Locators.profilePage;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BLANK;

/**
 * Created by Atmel on 20.08.2017.
 */
public class GeneralActions {

    private WebDriver driver;
    private WebDriverWait wait;
    String personURL;

    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 60);
    }

    public void openMyNetworkPage(String login, String password) throws IOException, InterruptedException {

        loginPage loginPage = PageFactory.initElements(driver, loginPage.class);
        peopleList peopleList = PageFactory.initElements(driver, peopleList.class);

        driver.navigate().to("https://www.linkedin.com/");
///*
        waitForContentLoad(loginPage.emailField);

        loginPage.emailField.click();
        loginPage.emailField.sendKeys(login);

        loginPage.passwordField.click();
        loginPage.passwordField.sendKeys(password);

        Thread.sleep(delay());
        loginPage.submitButton.click();
//*/
        waitForContentLoad(peopleList.myNetworkButton);
        peopleList.myNetworkButton.click();

        waitForContentLoad(peopleList.listOfPeople);

       Thread.sleep(5000);

    }

    public ArrayList<String> getAttributesOfHRFromExel (String pathToXls, int list) throws IOException {

        ArrayList<String> hrAttributes = new ArrayList<String>();

        File file = new File(pathToXls);
        HSSFWorkbook workBook = new HSSFWorkbook(new FileInputStream(file));

        HSSFSheet sheet = workBook.getSheetAt(list);
        int quantityRows = sheet.getLastRowNum() + 1;
        System.out.println("Кол-во строк в эксель: " + quantityRows);

        for (int i = 0; i < quantityRows; i++) {

            HSSFRow row = sheet.getRow(i);

            if (row != null) {
                Cell cell = row.getCell(0, Row.CREATE_NULL_AS_BLANK);

                if (cell != null && cell.getCellType() != CELL_TYPE_BLANK) {
                    String attribute = row.getCell(0).getStringCellValue().trim();

                    hrAttributes.add(attribute);
                }
            }
        }

        return hrAttributes;
    }

    public boolean searchOfNeededPeople (ArrayList<String> collectionFromExcel, int i, boolean state)
            throws InterruptedException {

        peopleList peopleList = PageFactory.initElements(driver, peopleList.class);

        if (i > 6) {

            scrollToTheLastProduct(i);
        }

        String personDescription = peopleList.neededPerson(i).getText().toLowerCase();
        personURL = peopleList.profileUrl(i).getAttribute("href");

        for (String stringa : collectionFromExcel) {

            //System.out.println("Поиск по аттрибутам/URL = " + stringa);

            if (state) {

                if (personDescription.contains(stringa.toLowerCase())) {

                    Thread.sleep(miniDelay());

                    System.out.println("Имя = " + peopleList.personName(i).getText());
                    System.out.println("Есть совпадение по аттрибуту" + "\n");
                    return state;
                }

            } else {

                if (personURL.contains(stringa.toLowerCase())) {

                    Thread.sleep(miniDelay());

                    System.out.println("URL = " + personURL);
                    System.out.println("Есть совпадение по URL" + "\n");
                    return state;
                }
            }
        }

            //System.out.println("Количество элементов в одной из коллекций равно 0"); //такой тоже есть вариант, если лист экселя пустой

            if (state) System.out.println("Совпадения по аттрибуту не найдено.");
            else System.out.println("Совпадения по URL не найдено.");
            return !state;

        }

    public void waitForContentLoad(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public int delay() {
        int timeMinMiliSec = 5000;
        int timeMaxMiliSec = 10000;
        int range = (timeMaxMiliSec - timeMinMiliSec) + 1;
        int randomDelay = (int) (Math.random() * range + timeMinMiliSec);
        // System.out.println("Задержка: " + randomDelay + "\n");
        return randomDelay;
    }

    public int miniDelay() {
        int timeMinMiliSec = 4000;
        int timeMaxMiliSec = 7000;
        int range = (timeMaxMiliSec - timeMinMiliSec) + 1;
        int randomDelay = (int) (Math.random() * range + timeMinMiliSec);
        // System.out.println("Задержка: " + randomDelay + "\n");
        return randomDelay;
    }

    public void writeToExcelInvitedUrls (String pathToXls) throws IOException {

        File file = new File(pathToXls);
        HSSFWorkbook workBook = new HSSFWorkbook(new FileInputStream(file));

        HSSFSheet sheet = workBook.getSheetAt(1);
        int quantityRows = sheet.getLastRowNum() + 1;

        Row row = sheet.createRow(quantityRows);
        Cell name = row.createCell(0);

        name.setCellValue(personURL);
        workBook.write(new FileOutputStream(file));

    }

    public void openProfilePageAndSendInvite(int i) throws InterruptedException {

        peopleList peopleList = PageFactory.initElements(driver, peopleList.class);
        String profileUrl = peopleList.profileUrl(i).getAttribute("href");

        String parentHandle = driver.getWindowHandle(); // получение название табки с открытой страницей
        ((JavascriptExecutor) driver).executeScript("window.open(arguments[0])", profileUrl); // открыли вторую вкладку с профайлом

        String childHandle = driver.getWindowHandles().toArray()[1].toString(); // получение название табки с открытым контакт профайлом
        driver.switchTo().window(childHandle); // делаем активной вкладку с открытым профайлом
        System.out.println("Открыли вкладку с профайлом.");

        profilePage profilePage = PageFactory.initElements(driver, profilePage.class);
        System.out.println("Инциализировали PageFactory.");

        //waitForContentLoad(profilePage.profileImage);
        wait.until(ExpectedConditions.presenceOfElementLocated(profilePage.getProfileImage()));
        System.out.println("Дождались загрузки картинки профиля.");

        Thread.sleep(delay());

        if (profilePage.ifAlreadyInvited().size() != 0) {

            System.out.println("Вошли в условие.");

            profilePage.connectButton.click();
            System.out.println("Кликнули на Connect кнопку");

            Thread.sleep(miniDelay());

            waitForContentLoad(profilePage.sendNowButton);
            System.out.println("Дождались кнопки в окне подтверждения");

            profilePage.sendNowButton.click();
            System.out.println("Кликнули на кнопку в окне подтверждения");

            Thread.sleep(miniDelay());

        }

        else System.out.println("Приглашение уже отправлено");

        driver.close();

        driver.switchTo().window(parentHandle);

    }

    public void scrollToTheLastProduct(int i) throws InterruptedException {

        peopleList peopleList = PageFactory.initElements(driver, peopleList.class);

        if (ifScrollToNeededPerson(i)) scrollToNeededPerson(i);

        else {

            for (; ; ) {

                int quantityOfPeople = peopleList.howManyPeopleOnThePage().size();

                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
                        peopleList.neededPerson(quantityOfPeople));

                Thread.sleep(miniDelay());

                if (ifScrollToNeededPerson(i)) {

                    scrollToNeededPerson(i);
                    Thread.sleep(miniDelay());
                    break;

                }
            }

            Thread.sleep(delay());
        }
    }

    public boolean ifScrollToNeededPerson(int i) {

        peopleList peopleList = PageFactory.initElements(driver, peopleList.class);

        if (peopleList.ifPersonexists(i).size() != 0) return true;
        else return false;

    }

    public void scrollToNeededPerson(int i) {

        peopleList peopleList = PageFactory.initElements(driver, peopleList.class);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
                peopleList.neededPerson(i-3));

    }

}
