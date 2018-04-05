package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

// URL http://localhost:8080/
public class MainPageObject {

    private WebDriverWait wait;
    private final WebDriver driver;

    @FindBy (xpath = "//a[@href='/manage']")
    private WebElement hrefManage;

    public MainPageObject(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 30);

        // Провекрка того факта, что мы на верной странице.
        if ((!driver.getTitle().equals("ИнфоПанель [Jenkins]")) ||
                (!driver.getCurrentUrl().equals("http://localhost:8080/"))) {
            throw new IllegalStateException("Wrong site page!");
        }
    }

    public MainPageObject clickManage() {
        hrefManage.click();
        return this;
    }










}
