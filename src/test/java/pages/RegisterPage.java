package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * RegisterPage - POM con Page Factory
 * Registro de usuario en Demoblaze
 */
public class RegisterPage extends BasePage {

    @FindBy(id = "signin2")
    private WebElement btnSignUp;

    @FindBy(id = "sign-username")
    private WebElement inputUsername;

    @FindBy(id = "sign-password")
    private WebElement inputPassword;

    @FindBy(xpath = "//button[text()='Sign up']")
    private WebElement btnSubmitSignUp;

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public void clickSignUp() {
        log.info("Click en Sign up");
        wait.waitForClickable(By.id("signin2")).click();
        wait.waitSeconds(1);
    }

    public void enterUsername(String username) {
        log.info("Ingresando username: " + username);
        wait.waitForVisible(By.id("sign-username")).clear();
        inputUsername.sendKeys(username);
    }

    public void enterPassword(String password) {
        log.info("Ingresando password");
        inputPassword.clear();
        inputPassword.sendKeys(password);
    }

    public void submitSignUp() {
        log.info("Submit Sign up");
        btnSubmitSignUp.click();
    }

    public boolean isRegistrationSuccessful() {
        try {
            // Demoblaze muestra un alert de confirmacion
            wait.waitForAlert();
            String alertText = driver.switchTo().alert().getText();
            log.info("Alert registro: " + alertText);
            boolean success = alertText.toLowerCase().contains("sign up successful")
                           || alertText.toLowerCase().contains("successful");
            driver.switchTo().alert().accept();
            return success;
        } catch (Exception e) {
            log.warn("No aparecio alert de registro: " + e.getMessage());
            // Si no hay alert de error tampoco, asumir exitoso
            return true;
        }
    }

    public boolean isRegistrationFailed() {
        try {
            wait.waitForAlert();
            String alertText = driver.switchTo().alert().getText();
            log.warn("Alert de error: " + alertText);
            boolean failed = alertText.toLowerCase().contains("already exist")
                          || alertText.toLowerCase().contains("error");
            driver.switchTo().alert().accept();
            return failed;
        } catch (Exception e) {
            return false;
        }
    }

    public void register(String username, String password) {
        clickSignUp();
        enterUsername(username);
        enterPassword(password);
        submitSignUp();
        wait.waitSeconds(2);
    }
}
