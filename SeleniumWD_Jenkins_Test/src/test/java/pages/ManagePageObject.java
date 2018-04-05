package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

// http://localhost:8080/manage
public class ManagePageObject extends MyMethod {
    private WebDriverWait wait;
    private final WebDriver driver;

    @FindBy(xpath = "//dt[text()='Управление пользователями']")
    private List<WebElement> dtTextManageUsers;

    @FindBy(xpath = "//dd[text()='Создание, удаление и модификция пользователей, " +
            "имеющих право доступа к Jenkins']")
    private List<WebElement> ddText;

    @FindBy(xpath = "//a//dt[text()='Управление пользователями']")
    private WebElement hrefManageUsers;

    public ManagePageObject(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 30);

        // Провекрка того факта, что мы на верной странице.
        if ((!driver.getTitle().equals("Настройка Jenkins [Jenkins]")) ||
                (!driver.getCurrentUrl().equals("http://localhost:8080/manage"))) {
            throw new IllegalStateException("Wrong site page!");
        }
    }

    public boolean existsDtTextManageUsers() {
        return existsElement(dtTextManageUsers);
    }

    public boolean existsDdText() {
        return existsElement(ddText);
    }

    public ManagePageObject clickManageUsers() {
        hrefManageUsers.click();
        return this;
    }

}
