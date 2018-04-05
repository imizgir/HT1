package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

//http://localhost:8080/securityRealm/
public class UsersPageObject extends MyMethod{

    private WebDriverWait wait;
    private final WebDriver driver;
    String url = "http://localhost:8080/securityRealm/";


    @FindBy(xpath = "//a[@href='user/someuser/delete']")
    private WebElement hrefDelete;

    @FindBy(xpath = "//a[@href='user/admin/delete']")
    private WebElement hrefDeleteAdmin;

    @FindBy(xpath = "//a[text()='Создать пользователя']")
    private WebElement hrefCreateUser;

    @FindBy(xpath = "//tr//td//*[text()='someuser']")
    private WebElement tdSomeuser;

    // для поиска элемента на стронице
    @FindBy(xpath = "//a[text()='Создать пользователя']")
    private List<WebElement> hrefsCreateUser;

    @FindBy(xpath = "//tr//td//*[text()='someuser']")
    private List<WebElement> tdsSomeuser;

    @FindBy(xpath = "//a[@href='user/someuser/delete']")
    private List<WebElement> hrefsDelete;

    @FindBy(xpath = "//a[@href='user/admin/delete']")
    private List<WebElement> hrefsDeleteAdmin;


    public UsersPageObject(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 30);

        // Провекрка того факта, что мы на верной странице.
        if ((!driver.getTitle().equals("Пользователи [Jenkins]")) ||
                (!driver.getCurrentUrl().equals(url))) {
            throw new IllegalStateException("Wrong site page!");
        }
    }


    public UsersPageObject clickHrefDelete() {
        hrefDelete.click();
        return this;
    }

    public UsersPageObject clickHrefCreteUser() {
        hrefCreateUser.click();
        return this;
    }

    public boolean existsHrefCreateUser() {
        return existsElement(hrefsCreateUser);
    }

    public boolean existsTdSomeuser() {
        return existsElement(tdsSomeuser);
    }

    public boolean existsHrefDelete() {
        return existsElement(hrefsDelete);
    }

    public boolean existsHrefDeleteAdmin() {
        return existsElement(hrefsDeleteAdmin);
    }
}
