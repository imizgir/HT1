package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DeleteUserPageObject {

    private WebDriverWait wait;
    private final WebDriver driver;
    String url = "http://localhost:8080/securityRealm/user/someuser/delete";

    // Подготовка элементов страницы.
    @FindBy(xpath = "//body")
    private WebElement body;

    @FindBy(xpath = "//button[text()='Да']")
    private WebElement submit_button;


    public DeleteUserPageObject(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 30);

        // Провекрка того факта, что мы на верной странице.
        if ((!driver.getTitle().equals("Jenkins")) ||
                (!driver.getCurrentUrl().equals(url))) {
            throw new IllegalStateException("Wrong site page!");
        }
    }

    // Проверка вхождения подстроки в текст страницы.
    public boolean pageTextContains(String search_string) {
        return body.getText().contains(search_string);
    }

    public String getErrorOnTextAbsence(String search_string) {
        if (!pageTextContains(search_string)) {
            return "No '" + search_string + "' is found inside page text!\n";
        } else {
            return "";
        }
    }

    public DeleteUserPageObject submitDelete() {
        submit_button.click();
        return this;
    }




}
