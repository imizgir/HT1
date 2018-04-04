package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPageObject {

    private WebDriverWait wait;
    private final WebDriver driver;

    // Подготовка элементов страницы.
    By manage_locator = By.xpath("//*[@id=\"tasks\"]/div[4]/a[2]");



    public MainPageObject(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 30);

        // Провекрка того факта, что мы на верной странице.
        if ((!driver.getTitle().equals("ИнфоПанель [Jenkins]")) ||
                (!driver.getCurrentUrl().equals("http://localhost:8080/"))) {
            throw new IllegalStateException("Wrong site page!");
        }
    }









}
