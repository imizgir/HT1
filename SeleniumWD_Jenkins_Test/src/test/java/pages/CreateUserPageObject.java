package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Collection;
import java.util.Iterator;

// класс страницы создания пользователя
// URL http://localhost:8080/securityRealm/addUser
public class CreateUserPageObject {

    private WebDriverWait wait;
    private final WebDriver driver;
    String url = "http://localhost:8080/securityRealm/addUser";

    // Подготовка элементов страницы.
    @FindBy(xpath = "//body")
    private WebElement body;

    @FindBy(xpath = "//form[@id='/securityRealm/createAccountByAdmin']")
    private WebElement form;

    @FindBy(name = "username")
    private WebElement username;

    @FindBy(name = "password1")
    private WebElement password1;

    @FindBy(name = "password2")
    private WebElement password2;

    @FindBy(name = "fullname")
    private WebElement fullname;

    @FindBy(name = "email")
    private WebElement email;

    @FindBy(xpath = "//button[text()='Создать пользователя']")
    private WebElement submit_button;


    public CreateUserPageObject(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 30);

        // Провекрка того факта, что мы на верной странице.
        if ((!driver.getTitle().equals("Создать пользователя [Jenkins]")) ||
                (!driver.getCurrentUrl().equals(url))) {
            throw new IllegalStateException("Wrong site page!");
        }
    }

    // Заполнение имени
    public CreateUserPageObject setUsername(String value) {
        username.clear();
        username.sendKeys(value);
        return this;
    }

    // Заполнение пароля
    public CreateUserPageObject setPassword(String value) {
        password1.clear();
        password1.sendKeys(value);
        return this;
    }

    // подтверждение пароля
    public CreateUserPageObject setConfirmPassword(String value) {
        password2.clear();
        password2.sendKeys(value);
        return this;
    }

    // заполнение ФИО
    public CreateUserPageObject setFullname(String value) {
        fullname.clear();
        fullname.sendKeys(value);
        return this;
    }

    // заполнение email
    public CreateUserPageObject setEmail(String value) {
        email.clear();
        email.sendKeys(value);
        return this;
    }

    // Заполнение всех полей формы.
    public CreateUserPageObject setFields(String username, String password, String confirmPassword, String fullname, String email) {
        setUsername(username);
        setPassword(password);
        setConfirmPassword(confirmPassword);
        setFullname(fullname);
        setEmail(email);
        return this;
    }

    // Отправка данных из формы.
    public CreateUserPageObject submitForm() {
        submit_button.click();
        return this;
    }

    // Надёжный поиск формы.
    public boolean isFormPresentWithEmptyFields() {
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

        Collection<WebElement> forms = driver.findElements(By.tagName("form"));
        if (forms.isEmpty()) {
            return false;
        }

        Iterator<WebElement> i = forms.iterator();
        boolean result = false;
        WebElement form = null;


        while (i.hasNext()) {
            form = i.next();
            if ((form.findElement(By.xpath("//input[@type='text']")).getAttribute("value").isEmpty()) &&
                    (form.findElement(By.xpath("//input[@type='password']")).getAttribute("value").isEmpty()) &&
                    (form.findElement(By.xpath("//input[@type='password']")).getAttribute("value").isEmpty()) &&
                    (form.findElement(By.xpath("//input[@type='text']")).getAttribute("value").isEmpty()) &&
                    (form.findElement(By.xpath("//input[@type='text']")).getAttribute("value").isEmpty())) {
                result = true;
            }
        }
        return result;
    }

    // Получение значения имени.
    public String getUsername() {
        return username.getAttribute("value");
    }

    public String getPassword1() {
        return password1.getAttribute("value");
    }

    public String getPassword2() {
        return password2.getAttribute("value");
    }

    public String getFullname() {
        return fullname.getAttribute("value");
    }

    public String getEmail() {
        return email.getAttribute("value");
    }


}
