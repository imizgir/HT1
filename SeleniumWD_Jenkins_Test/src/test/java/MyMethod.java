import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.AuthorizationPageObject;

import java.util.Collection;
import java.util.Iterator;

public class MyMethod {

    WebDriver driver = null;
    String baseURL = "http://localhost:8080";
    StringBuffer verificationErrors = new StringBuffer();

    public MyMethod(WebDriver driver) {
        this.driver = driver;
    }

    public void login() {

        driver.get(baseURL + "/login?from=%2F");
        AuthorizationPageObject page = new AuthorizationPageObject(driver);
        Assert.assertTrue(page.isFormPresentForRealWithXPath(), "No suitable forms found!");
        verificationErrors.append(page.getErrorOnTextAbsence("Логин:"));
        verificationErrors.append(page.getErrorOnTextAbsence("Пароль:"));
        verificationErrors.append(page.getErrorOnTextAbsence("Запомнить меня"));

        page.setUsername("imizgir");
        Assert.assertEquals(page.getUsername(), "imizgir", "Unable to fill 'Логин' field");

        page.setPassword("imizgirpass");
        Assert.assertEquals(page.getPassword(), "imizgirpass", "Unable to fill 'Пароль' field");

        page.setRememberMe("");
        Assert.assertEquals(page.getRememberMe(), "", "Unable select 'Запомнить меня' gender");

        page.submitForm();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(page.getProfileLinkLocator()));

        Assert.assertEquals(page.getProfileLink(), baseURL + "/user/imizgir", "Unable select 'Запомнить меня' gender");

    }

    public Collection<WebElement> getElementsWithTag (String tagName){
        Collection<WebElement> elementsWithTag = driver.findElements(By.tagName(tagName));
        if (elementsWithTag.isEmpty()) {
            return null;
        }
        return elementsWithTag;
    }

    public WebElement getElementWithTagAndAttribute (String tagName, String attribute, String attValue){

        WebElement element = null;

        Iterator<WebElement> i = getElementsWithTag(tagName).iterator();
        WebElement elementWithTag = null;

        while (i.hasNext()) {
            elementWithTag = i.next();
            if (elementWithTag.getAttribute(attribute).equalsIgnoreCase(attValue)){
                element = elementWithTag;
                break;
            }
        }

        return element;
    }

    public WebElement getElementWithTagAndText (String tagName, String text){

        WebElement element = null;

        Iterator<WebElement> i = getElementsWithTag(tagName).iterator();
        WebElement elementWithTag = null;

        while (i.hasNext()) {
            elementWithTag = i.next();
            if (elementWithTag.getText().equalsIgnoreCase(text)){
                element = elementWithTag;
                break;
            }
        }

        return element;
    }


}
