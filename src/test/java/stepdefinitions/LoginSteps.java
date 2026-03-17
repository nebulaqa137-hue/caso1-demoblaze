package stepdefinitions;

import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.LoginPage;
import utils.ConfigManager;
import utils.DriverFactory;

/**
 * StepDefinitions - Login
 * Conecta los steps de Gherkin con el codigo Java via Page Object
 */
public class LoginSteps {

    private LoginPage loginPage;
    private static final ConfigManager config = ConfigManager.getInstance();

    public LoginSteps() {
        this.loginPage = new LoginPage(DriverFactory.getDriver());
    }

    @Given("el usuario esta en la pagina de Demoblaze")
    public void elUsuarioEstaEnLaPaginaDeDemoblaze() {
        // Driver y navegacion los maneja Hooks
        loginPage = new LoginPage(DriverFactory.getDriver());
    }

    @When("el usuario hace click en Login")
    public void elUsuarioHaceClickEnLogin() {
        loginPage.clickLoginButton();
    }

    @When("ingresa el usuario valido y contrasena valida")
    public void ingresaUsuarioValidoYContrasenaValida() {
        loginPage.enterUsername(config.getValidUser());
        loginPage.enterPassword(config.getValidPass());
        loginPage.submitLogin();
    }

    @When("ingresa el usuario invalido y contrasena invalida")
    public void ingresaUsuarioInvalidoYContrasenaInvalida() {
        loginPage.enterUsername(config.getInvalidUser());
        loginPage.enterPassword(config.getInvalidPass());
        loginPage.submitLogin();
    }

    @Then("debe ver el mensaje de bienvenida")
    public void debeVerElMensajeDeBienvenida() {
        Assert.assertTrue(loginPage.isLoggedIn(),
            "El usuario deberia estar logueado y ver el mensaje de bienvenida");
    }

    @Then("no debe estar logueado")
    public void noDebeEstarLogueado() {
        Assert.assertFalse(loginPage.isLoggedIn(),
            "El usuario NO deberia estar logueado con credenciales invalidas");
    }

    @And("el usuario esta logueado")
    public void elUsuarioEstaLogueado() {
        loginPage = new LoginPage(DriverFactory.getDriver());
        loginPage.clickLoginButton();
        loginPage.enterUsername(config.getValidUser());
        loginPage.enterPassword(config.getValidPass());
        loginPage.submitLogin();
        loginPage.isLoggedIn();
    }

    @And("el usuario hace logout")
    public void elUsuarioHaceLogout() {
        loginPage.logout();
    }

    @Then("el boton de Login debe ser visible")
    public void elBotonDeLoginDebeSerVisible() {
        Assert.assertFalse(loginPage.isLoggedIn(),
            "Despues del logout el usuario no debe estar logueado");
    }
}
