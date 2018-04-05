import org.openqa.selenium.WebDriver;
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
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            Assert.fail(verificationErrorString);
        }
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        // проверяем, выполнена ли авторизация
        // если нет, выполняем авторизацию
        AuthorizationPageObject authorizationPageObject = new AuthorizationPageObject(driver);
        if (!authorizationPageObject.isLogined()) {
            MyMethod myMethod = new MyMethod(driver);
            myMethod.login();
        }
    }


    // тестируем авторизацию
    @Test
    public void tstAuthorization() {

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

    // После клика по ссылке «Manage Jenkins» на странице
    // появляется элемент dt с текстом «Manage Users» и элемент
    // dd с текстом «Create/delete/modify users that can log in
    // to this Jenkins».
    @Test
    public void tstManageUsers() {

        driver.get(baseURL);

        MainPageObject mainPage = PageFactory.initElements(driver, MainPageObject.class);
        mainPage.clickManage();

        ManagePageObject managePage = PageFactory.initElements(driver, ManagePageObject.class);
        Assert.assertTrue(managePage.existsDtTextManageUsers());
        Assert.assertTrue(managePage.existsDdText());
    }

    // После клика по ссылке, внутри которой содержится элемент
    // dt с текстом «Manage Users», становится доступна ссылка
    // «Create User».
    @Test
    public void tstAddUsers() {

        driver.get(baseURL + "/manage");

        // ищем элемент dt с текстом «Manage Users» и кликаем по ссылке
        ManagePageObject managePage = PageFactory.initElements(driver, ManagePageObject.class);
        managePage.clickManageUsers();

        // ищем ссылку «Create User» и проверяем её доступность
        UsersPageObject usersPage = PageFactory.initElements(driver, UsersPageObject.class);
        Assert.assertTrue(usersPage.existsHrefCreateUser());

    }

    // После клика по ссылке «Create User» появляется форма с
    // тремя полями типа text и двумя полями типа password,
    // причём все поля должны быть пустыми.
    @Test
    public void tstCreateUserForm() {

        driver.get(baseURL + "/securityRealm/");

        // кликаем по ссылке «Create User»
        UsersPageObject usersPage = PageFactory.initElements(driver, UsersPageObject.class);
        usersPage.clickHrefCreteUser();

        // ищем форму с тремя полями типа text и двумя полями типа password
        // при этом, все поля пустые
        CreateUserPageObject createUserPage = new CreateUserPageObject(driver);
        Assert.assertTrue(createUserPage.isFormPresentWithEmptyFields(), "No suitable forms found!");
    }

    // После заполнения полей формы («Username» = «someuser»,
    // «Password» = «somepassword», «Confirm password» =
    // «somepassword», «Full name» = «Some Full Name»,
    // «E-mail address» = «some@addr.dom») и клика по кнопке
    // с надписью «Create User» на странице появляется строка
    // таблицы (элемент tr), в которой есть ячейка (элемент td)
    // с текстом «someuser».
    @Test
    public void tstCreateUserFormSubmit() {

        // переходим на страницу Manage Jenkins http://localhost:8080/securityRealm/addUser
        driver.get(baseURL + "/securityRealm/addUser");

        // заполняем форму
        CreateUserPageObject createUserPage = PageFactory.initElements(driver, CreateUserPageObject.class);

        createUserPage.setUsername("someuser");
        Assert.assertEquals(createUserPage.getUsername(), "someuser", "Unable to fill 'Имя пользователя:' field");

        createUserPage.setPassword("somepassword");
        Assert.assertEquals(createUserPage.getPassword1(), "somepassword", "Unable to fill 'Пароль:' field");

        createUserPage.setConfirmPassword("somepassword");
        Assert.assertEquals(createUserPage.getPassword2(), "somepassword", "Unable to fill 'Повторите пароль:' field");

        createUserPage.setFullname("Some Full Name");
        Assert.assertEquals(createUserPage.getFullname(), "Some Full Name", "Unable to fill 'Ф.И.О.:' field");

        createUserPage.setEmail("some@addr.dom");
        Assert.assertEquals(createUserPage.getEmail(), "some@addr.dom", "Unable to fill 'Адрес электронной почты:' field");

        // кликаем по кнопке с надписью «Create User»
        createUserPage.submitForm();

        // ищем строку таблицы (элемент tr), в которой есть
        // ячейка (элемент td) с текстом «someuser».
        UsersPageObject usersPageObject = PageFactory.initElements(driver, UsersPageObject.class);
        Assert.assertTrue(usersPageObject.existsTdSomeuser());
    }

    // После клика по ссылке с атрибутом href равным
    // «user/someuser/delete» появляется текст «Are you
    // sure about deleting the user from Jenkins?».
    @Test(dependsOnMethods = "tstCreateUserFormSubmit")
    public void tstDeleteUser() {

        driver.get(baseURL + "/securityRealm");

        // кликаем по ссылке с атрибутом href равным «user/someuser/delete»
        UsersPageObject usersPage = PageFactory.initElements(driver, UsersPageObject.class);
        usersPage.clickHrefDelete();

        // ищем текст «Are you sure about deleting the user from Jenkins?»
        DeleteUserPageObject deleteUserPageObject = PageFactory.initElements(driver, DeleteUserPageObject.class);
        deleteUserPageObject.pageTextContains("Вы уверены, что хотите удалить пользователя из Jenkins?");
    }

    // После клика по кнопке с надписью «Yes» на странице
    // отсутствует строка таблицы (элемент tr), с ячейкой
    // (элемент td) с текстом «someuser». На странице
    // отсутствует ссылка с атрибутом href равным
    // «user/someuser/delete».
    @Test(dependsOnMethods = "tstDeleteUser")
    public void tstConfirmDeleteUser() {

        driver.get(baseURL + "/securityRealm/user/someuser/delete");

        // кликаем по кнопке с надписью «Yes»
        DeleteUserPageObject deleteUserPageObject = PageFactory.initElements(driver, DeleteUserPageObject.class);
        deleteUserPageObject.submitDelete();

        UsersPageObject usersPageObject = PageFactory.initElements(driver, UsersPageObject.class);
        // ищем строку таблицы (элемент tr), с ячейкой (элемент td) с текстом «someuser»
        Assert.assertFalse(usersPageObject.existsTdSomeuser(), "Element is on the page!");
        // ищем ссылка с атрибутом href равным «user/someuser/delete»
        Assert.assertFalse(usersPageObject.existsHrefDelete(), "Element is on the page!");
    }

    //{На той же странице, без выполнения каких бы то ни
    // было действий}. На странице отсутствует ссылка с
    // атрибутом href равным «user/admin/delete».
    @Test
    public void tstDeleteAdmin() {

        driver.get(baseURL + "/securityRealm");

        // ищем ссылка с атрибутом href равным «user/admin/delete»
        UsersPageObject usersPageObject = PageFactory.initElements(driver, UsersPageObject.class);
        Assert.assertFalse(usersPageObject.existsHrefDeleteAdmin(), "Form is on the page!");
    }

}
