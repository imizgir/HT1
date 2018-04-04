import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;

import java.util.Arrays;

public class TestJenkins {

    WebDriver driver = null;
    String baseURL = "http://localhost:8080";
    StringBuffer verificationErrors = new StringBuffer();

    @BeforeClass
    public void beforeClass() throws Exception {
        System.setProperty("webdriver.chrome.driver", "D:/Education/EPAM/chromedriver_win32/chromedriver.exe");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability("chrome.switches", Arrays.asList("--homepage=about:blank"));
        driver = new ChromeDriver(capabilities);

    }

    @AfterClass
    public void afterClass() {
        //driver.quit();
        //String verificationErrorString = verificationErrors.toString();
        //if (!"".equals(verificationErrorString)) {
        //    Assert.fail(verificationErrorString);
        //}
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        AuthorizationPageObject authorizationPageObject = new AuthorizationPageObject(driver);
        if (!authorizationPageObject.isLogined()){
            MyMethod myMethod = new MyMethod(driver);
            myMethod.login();
        }

    }


    // Testing authorization
    @Test
    public void tstAuthorization() {
        // 1-действие: "Открыть http://svyatoslav.biz/testlab/wt/"
        driver.get(baseURL + "/login?from=%2F");

        // С этого момента можно использовать PajeObject.
        AuthorizationPageObject page = new AuthorizationPageObject(driver);

        // 1-проверка: "Страница содержит форму с полями «Имя», «Рост», «Вес», радиокнопкой «Пол» и
        // кнопкой отправки данных «Рассчитать». Также на странице есть соответствующие текстовые надписи."
        Assert.assertTrue(page.isFormPresentForRealWithXPath(), "No suitable forms found!");
        //Assert.assertTrue(page.isFormPresentForReal(), "No suitable forms found!");
        verificationErrors.append(page.getErrorOnTextAbsence("Логин:"));
        verificationErrors.append(page.getErrorOnTextAbsence("Пароль:"));
        verificationErrors.append(page.getErrorOnTextAbsence("Запомнить меня"));

        // 2-действие: "В поле «Имя» ввести «username»."
        page.setUsername("imizgir");

        // 2-проверка: "Значение появляется в поле."
        Assert.assertEquals(page.getUsername(), "imizgir", "Unable to fill 'Логин' field");

        // 3-действие: "В поле «Рост» ввести «50»."
        page.setPassword("imizgirpass");

        // 3-проверка: "Значение появляется в поле."
        Assert.assertEquals(page.getPassword(), "imizgirpass", "Unable to fill 'Пароль' field");

        // 5-действие: "В радиокнопке «Пол» выбрать пол «М»."
        page.setRememberMe("");

        // 5-проверка: "Вариант «М» выбран."
        Assert.assertEquals(page.getRememberMe(), "", "Unable select 'Запомнить меня' gender");


        // 6-действие: "6. Нажать «Рассчитать»."
        page.submitForm();

        // 6-проверка: "6. Форма исчезает, в центральной ячейке таблицы появляется надпись «Слишком малая масса тела»."
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(page.getProfileLinkLocator()));

        //Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"header\"]/div[2]/span/a[1]")).getAttribute("href"), "http://localhost:8080/user/imizgir");

        Assert.assertEquals(page.getProfileLink(), baseURL + "/user/imizgir", "Unable select 'Запомнить меня' gender");

    }

    @Test
    public void tstManageUsers() {
        // авторизация
       // MyMethod myMethod = new MyMethod(driver);
       // myMethod.login();

        driver.get(baseURL + "/manage");

        ManagePageObject page = new ManagePageObject(driver);

        Assert.assertTrue(page.isElementWithTagAndTextPresentForReal("dt", "Управление пользователями"),
                "[No suitable tagName found!]");
        Assert.assertTrue(page.isElementWithTagAndTextPresentForReal("dd", "Создание, удаление и " +
                "модификция пользователей, имеющих право доступа к Jenkins"), "[No suitable tagName found!]");

    }

    @Test
    public void tstAddUsers() {
        // авторизация
        MyMethod myMethod = new MyMethod(driver);
       // myMethod.login();

        // переходим на страницу Manage Jenkins http://localhost:8080/manage
        driver.get(baseURL + "/manage");

        // ищем элемент dt с текстом «Manage Users» и кликаем по ссылке
        ManagePageObject managePage = new ManagePageObject(driver);
        myMethod.getElementWithTagAndText("dt", "Управление пользователями").click();

        // ищем ссылку «Create User» и проверяем её доступность
        UsersPageObject usersPage = new UsersPageObject(driver);
        Assert.assertTrue(myMethod.getElementWithTagAndText("a", "Создать пользователя").isEnabled());
    }

    @Test
    public void tstCreateUserForm() {
        // авторизация
        MyMethod myMethod = new MyMethod(driver);
       // myMethod.login();

        // переходим на страницу Manage Jenkins http://localhost:8080/manage
        driver.get(baseURL + "/securityRealm/");

        // ищем ссылку «Create User» и кликаем по ссылке
        UsersPageObject usersPage = new UsersPageObject(driver);
        myMethod.getElementWithTagAndText("a", "Создать пользователя").click();

        // ищем форму с тремя полями типа text и двумя полями типа password
        // проверяем, все ли поля пустые
        CreateUserPageObject createUserPage = new CreateUserPageObject(driver);
        WebElement form = createUserPage.getFormPresentForReal();
        Assert.assertTrue(form.isDisplayed(), "No suitable forms found!");
        Assert.assertTrue(createUserPage.isInputsEmpty(form), "Inputs NO empty!");
    }

    @Test
    public void tstCreateUserFormSubmit() {
        // авторизация
       //MyMethod myMethod = new MyMethod(driver);
       // myMethod.login();

        // переходим на страницу Manage Jenkins http://localhost:8080/securityRealm/addUser
        driver.get(baseURL + "/securityRealm/addUser");

        // ищем форму с тремя полями типа text и двумя полями типа password
        // проверяем, отображается ли форма
        CreateUserPageObject createUserPage = PageFactory.initElements(driver, CreateUserPageObject.class);
        //Assert.assertTrue(createUserPage.getFormPresentForReal().isDisplayed(), "No suitable forms found!");

        // 2-действие: "В поле «Имя» ввести «username»."
        createUserPage.setUsername("someuser");
        Assert.assertEquals(createUserPage.getUsername(), "someuser", "Unable to fill 'Имя' field");

        createUserPage.setPassword("somepassword");
        Assert.assertEquals(createUserPage.getPassword1(), "somepassword", "Unable to fill 'Имя' field");

        createUserPage.setConfirmPassword("somepassword");
        Assert.assertEquals(createUserPage.getPassword2(), "somepassword", "Unable to fill 'Имя' field");

        createUserPage.setFullname("Some Full Name");
        Assert.assertEquals(createUserPage.getFullname(), "Some Full Name", "Unable to fill 'Имя' field");

        createUserPage.setEmail("some@addr.dom");
        Assert.assertEquals(createUserPage.getEmail(), "some@addr.dom", "Unable to fill 'Имя' field");


        // 6-действие: "6. Нажать «Рассчитать»."
        createUserPage.submitForm();

        driver.get(baseURL + "/securityRealm");
        // 6-проверка: "6. Форма исчезает, в центральной ячейке таблицы появляется надпись «Слишком малая масса тела»."

        UsersPageObject usersPageObject = PageFactory.initElements(driver, UsersPageObject.class);

        // не находит, потому что:
        // <td>
        //  <a href="user/someuser/">someuser</a>
        // </td>
        Assert.assertTrue(usersPageObject.isTrWithTdWithTextPresentForReal(), "[No suitable tagName found!]");
    }

    @Test (dependsOnMethods = "tstCreateUserFormSubmit")
    public void tstDeleteUser() {
        // авторизация
       // MyMethod myMethod = new MyMethod(driver);
       // myMethod.login();

        // переходим на страницу Manage Jenkins http://localhost:8080/securityRealm/addUser
        driver.get(baseURL + "/securityRealm");
        // ищем форму с тремя полями типа text и двумя полями типа password
        // проверяем, отображается ли форма
        UsersPageObject usersPageObject = PageFactory.initElements(driver, UsersPageObject.class);
        usersPageObject.clickHrefDelete();

        DeleteUserPageObject deleteUserPageObject = PageFactory.initElements(driver, DeleteUserPageObject.class);
        deleteUserPageObject.pageTextContains("\n" +
                "        Вы уверены, что хотите удалить пользователя из Jenkins?\n" +
                "        ");
    }

    @Test (dependsOnMethods = "tstDeleteUser")
    public void tstConfirmDeleteUser() {

        // авторизация
      //  MyMethod myMethod = new MyMethod(driver);
       // myMethod.login();

        // переходим на страницу Manage Jenkins http://localhost:8080/securityRealm/addUser
        driver.get(baseURL + "/securityRealm/user/someuser/delete");
        // ищем форму с тремя полями типа text и двумя полями типа password
        // проверяем, отображается ли форма
        DeleteUserPageObject deleteUserPageObject = PageFactory.initElements(driver, DeleteUserPageObject.class);
        deleteUserPageObject.submitDelete();

        driver.get(baseURL + "/securityRealm");

        UsersPageObject usersPageObject = PageFactory.initElements(driver, UsersPageObject.class);
        Assert.assertFalse(usersPageObject.isTdSomeuserPresentIsReal(), "Form is on the page!");
        Assert.assertFalse(usersPageObject.isAHrefPresentIsReal(), "Form is on the page!");
    }

    @Test
    public void tstDeleteAdmin() {

        // авторизация
      //  MyMethod myMethod = new MyMethod(driver);
       // myMethod.login();

        driver.get(baseURL + "/securityRealm");
        UsersPageObject usersPageObject = PageFactory.initElements(driver, UsersPageObject.class);
        Assert.assertFalse(usersPageObject.isAHrefDeleteAdminPresentIsReal(), "Form is on the page!");
    }



}
