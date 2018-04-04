package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Collection;
import java.util.Iterator;

public class ManagePageObject {
    private WebDriverWait wait;
    private final WebDriver driver;



    public ManagePageObject(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 30);

        // Провекрка того факта, что мы на верной странице.
        if ((!driver.getTitle().equals("Настройка Jenkins [Jenkins]")) ||
                (!driver.getCurrentUrl().equals("http://localhost:8080/manage"))) {
            throw new IllegalStateException("Wrong site page!");
        }
    }

    public boolean isElementWithTagAndTextPresentForReal(String tagName, String text) {
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


        Collection<WebElement> tags = driver.findElements(By.tagName(tagName));
        if (tags.isEmpty()) {
            return false;
        }

        Iterator<WebElement> i = tags.iterator();
        boolean element_found = false;
        WebElement tag = null;

        while (i.hasNext()) {
            tag = i.next();
            if (tag.getText().equalsIgnoreCase(text)){
                element_found = true;
                break;
            }
        }

        return element_found;
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




}
