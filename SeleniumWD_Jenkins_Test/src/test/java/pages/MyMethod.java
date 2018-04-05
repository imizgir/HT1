package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

public class MyMethod {

    WebDriver driver = null;
    String baseURL = "http://localhost:8080";
    StringBuffer verificationErrors = new StringBuffer();

    public MyMethod(WebDriver driver) {
        this.driver = driver;
    }

    public MyMethod() {
    }

    // авторизация
    public void login() {

        driver.get(baseURL + "/login?from=%2F");
        AuthorizationPageObject page = new AuthorizationPageObject(driver);
        Assert.assertTrue(page.isFormPresentForReal(), "No suitable forms found!");

        page.setUsername("imizgir");
        Assert.assertEquals(page.getUsername(), "imizgir", "Unable to fill 'Логин' field");

        page.setPassword("imizgirpass");
        Assert.assertEquals(page.getPassword(), "imizgirpass", "Unable to fill 'Пароль' field");

        page.setRememberMe("");
        Assert.assertEquals(page.getRememberMe(), "", "Unable select 'Запомнить меня' gender");

        page.submitForm();

        // проверяем, прошли ли авторизация
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(page.getProfileLinkLocator()));
        Assert.assertEquals(page.getProfileLink(), baseURL + "/user/imizgir");

    }

    // поиск элемента на странице
    public boolean existsElement(List<WebElement> list) {

        if (list.size() != 0) {
            return true;
        } else {
            return false;
        }
    }

    // поиск элемента на странице
    public boolean existsElementByLocator(By locator) {
        try {
            driver.findElement(locator);
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }
}
