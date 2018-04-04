package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Collection;
import java.util.Iterator;

public class UsersPageObject {

    private WebDriverWait wait;
    private final WebDriver driver;
    String url = "http://localhost:8080/securityRealm/";


    @FindBy(xpath = "//a[@href='user/someuser/delete']")
    private WebElement hrefDelete;

    @FindBy(xpath = "//a[@href='user/admin/delete']")
    private WebElement hrefDeleteAdmin;

    @FindBy(xpath = "//td//tr//*[text()='someuser']")
    private WebElement tdSomeuser;

    public UsersPageObject(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 30);

        // Провекрка того факта, что мы на верной странице.
        if ((!driver.getTitle().equals("Пользователи [Jenkins]")) ||
                (!driver.getCurrentUrl().equals(url))) {
            throw new IllegalStateException("Wrong site page!");
        }
    }

    public WebElement getElementWithTagAndText (String tagName, String text){

        WebElement element = null;
        Collection<WebElement> tags = driver.findElements(By.tagName(tagName));
        if (tags.isEmpty()) {
            return null;
        }

        Iterator<WebElement> i = tags.iterator();
        WebElement tag = null;

        while (i.hasNext()) {
            tag = i.next();
            if (tag.getText().equalsIgnoreCase(text)){
                element = tag;
                break;
            }
        }

        return element;
    }

    // Отправка данных из формы.
    public UsersPageObject clickHrefDelete() {
        hrefDelete.click();
        return this;
    }

    public WebElement getHrefDelete() {
        return hrefDelete;
    }

    // Надёжный поиск формы.
    public boolean isTrWithTdWithTextPresentForReal() {
        // Первое (самое правильное) решение (работает примерно в 30-50% случаев)
        // wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//html/body"), 1));

        // Второе (самое интересное) решение (работает примерно в 20-30% случаев; не работает в 3.3.1)
        // waitForLoad(driver);

        // Третье (самое убогое, почти за гранью запрещённого) решение -- работает в 100% случаев

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Collection<WebElement> trs = driver.findElements(By.tagName("tr"));
        if (trs.isEmpty()) {
            return false;
        }

        Iterator<WebElement> i = trs.iterator();
        boolean element_found = false;
        WebElement tr = null;

        while (i.hasNext()) {
            tr = i.next();
            if (
                //(tr.findElement(By.tagName("td")).getText().equalsIgnoreCase("someuser"))||
                    (tr.findElement(By.xpath("//td//*[text()=\"someuser\"]")).isDisplayed())
                    ) {
                element_found = true;
                break;
            }
        }

        return element_found;
    }

    // Упрощённый поиск формы.
    public boolean isTdSomeuserPresent() {

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        if (tdSomeuser.getText().equalsIgnoreCase("someuser")) {
            return true;
        } else {
            return false;
        }
    }

    // работает вроде
    // Упрощённый поиск формы.
    public boolean isTdSomeuserPresentIsReal() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Collection<WebElement> trs = driver.findElements(By.xpath("//tr//td//*[text()='someuser']"));
        if (trs.isEmpty()) {
            return false;
        } else {
            return true;
        }

    }

    public boolean isAHrefPresentIsReal() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Collection<WebElement> trs = driver.findElements(By.xpath("//a[@href='user/someuser/delete']"));
        if (trs.isEmpty()) {
            return false;
        } else {
            return true;
        }

    }

    public boolean isAHrefDeleteAdminPresentIsReal() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Collection<WebElement> trs = driver.findElements(By.xpath("//a[@href='user/admin/delete']"));
        if (trs.isEmpty()) {
            return false;
        } else {
            return true;
        }

    }
}
