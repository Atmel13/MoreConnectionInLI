import Locators.peopleList;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Atmel on 20.08.2017.
 */
public class mainScript extends baseActions {

    @Parameters ({"login", "password", "HRPath"})
    @Test
    public void script (String login, String password, String HRPath)throws IOException, InterruptedException {

        ArrayList<String> HRattributesList = actions.getAttributesOfHRFromExel(HRPath, 0);
        ArrayList<String> HRUrlsList = actions.getAttributesOfHRFromExel(HRPath,1);

        actions.openMyNetworkPage(login, password);

        for (int i = 1;; i++) {

            peopleList peopleList = PageFactory.initElements(driver, peopleList.class);
            System.out.println("Имя = " + peopleList.personName(i).getText());

            System.out.println("Количество обработанных контактов = " + i + "\n");

            if (actions.searchOfNeededPeople(HRattributesList, i, true)
                    & actions.searchOfNeededPeople(HRUrlsList, i, false)) {

                actions.openProfilePageAndSendInvite(i);

                System.out.println("Отправили инвайт!" + '\n');
                actions.writeToExcelInvitedUrls(HRPath);

            }

        }

    }

}