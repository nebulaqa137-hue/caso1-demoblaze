package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * LoginPage - POM con Page Factory
 * Contiene locators (@FindBy) y acciones de la pagina de login de Demoblaze
 */
public class LoginPage extends BasePage {

    // ==================== PAGE FACTORY LOCATORS ====================
    @FindBy(id = "login2")
    private WebElement btnLogin;

    @FindBy(id = "loginusername")
    private WebElement inputUsername;

    @FindBy(id = "loginpassword")
    private WebElement inputPassword;

    @FindBy(xpath = "//button[text()='Log in']")
    private WebElement btnSubmitLogin;

    @FindBy(id = "nameofuser")
    private WebElement lblWelcome;

    @FindBy(id = "logout2")
    private WebElement btnLogout;

    // ==================== CONSTRUCTOR ====================
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // ==================== ACCIONES ====================
    public void clickLoginButton() {
        log.info("Click en boton Login");
        wait.waitForClickable(org.openqa.selenium.By.id("login2")).click();
    }

    public void enterUsername(String username) {
        log.info("Ingresando usuario: " + username);
        wait.waitForVisible(org.openqa.selenium.By.id("loginusername")).clear();
        inputUsername.sendKeys(username);
    }

    public void enterPassword(String password) {
        log.info("Ingresando contrasena");
        inputPassword.clear();
        inputPassword.sendKeys(password);
    }

    public void submitLogin() {
        log.info("Submit login");
        btnSubmitLogin.click();
    }

    public void login(String username, String password) {
        clickLoginButton();
        enterUsername(username);
        enterPassword(password);
        submitLogin();
        wait.waitSeconds(2);
    }

    public boolean isLoggedIn() {
        try {
            WebElement welcome = wait.waitForVisible(
                org.openqa.selenium.By.id("nameofuser"));
            log.info("Login exitoso. Bienvenido: " + welcome.getText());
            return welcome.isDisplayed();
        } catch (Exception e) {
            log.warn("No se mostro mensaje de bienvenida");
            return false;
        }
    }

    public String getWelcomeMessage() {
        try {
            return wait.waitForVisible(
                org.openqa.selenium.By.id("nameofuser")).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public void logout() {
        log.info("Haciendo logout");
        wait.waitForClickable(org.openqa.selenium.By.id("logout2")).click();
        wait.waitSeconds(1);
    }
}
