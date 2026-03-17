package stepdefinitions;

import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.RegisterPage;
import utils.ConfigManager;
import utils.DriverFactory;
import utils.RandomDataHelper;

/**
 * RegistroSteps - Caso 1 Demoblaze
 * Escenarios de registro con datos aleatorios via JavaFaker
 */
public class RegistroSteps {

    private RegisterPage registerPage;
    private static final ConfigManager config = ConfigManager.getInstance();

    // Guardamos los datos generados para reutilizarlos en el mismo escenario
    private String generatedUsername;
    private String generatedPassword;

    public RegistroSteps() {
        this.registerPage = new RegisterPage(DriverFactory.getDriver());
    }

    @When("el usuario hace click en Sign up")
    public void clickSignUp() {
        registerPage = new RegisterPage(DriverFactory.getDriver());
        registerPage.clickSignUp();
    }

    @And("ingresa datos de registro aleatorios")
    public void ingresaDatosAleatorios() {
        generatedUsername = RandomDataHelper.randomUsername();
        generatedPassword = RandomDataHelper.randomPassword();
        RandomDataHelper.logGeneratedData(generatedUsername, generatedPassword);
        registerPage.enterUsername(generatedUsername);
        registerPage.enterPassword(generatedPassword);
        registerPage.submitSignUp();
    }

    @Then("el registro debe ser exitoso")
    public void registroExitoso() {
        boolean success = registerPage.isRegistrationSuccessful();
        Assert.assertTrue(success,
            "El registro debe ser exitoso para usuario: " + generatedUsername);
    }

    @And("hace login con el usuario recien registrado")
    public void loginConUsuarioRegistrado() {
        // Usar los datos generados en el paso anterior
        Assert.assertNotNull(generatedUsername, "Debe existir un usuario registrado previamente");
        registerPage.enterUsername(generatedUsername);
        // Reutilizar LoginPage para ingresar contrasena y hacer submit
        DriverFactory.getDriver()
            .findElement(org.openqa.selenium.By.id("loginpassword"))
            .sendKeys(generatedPassword);
        DriverFactory.getDriver()
            .findElement(org.openqa.selenium.By.xpath("//button[text()='Log in']"))
            .click();
    }

    @And("ingresa el usuario existente {string} con su contrasena")
    public void ingresaUsuarioExistente(String username) {
        registerPage.enterUsername(username);
        registerPage.enterPassword(config.getValidPass());
        registerPage.submitSignUp();
    }

    @Then("debe aparecer mensaje de usuario ya existente")
    public void mensajeUsuarioExistente() {
        boolean failed = registerPage.isRegistrationFailed();
        Assert.assertTrue(failed,
            "Debe aparecer mensaje de error por usuario ya existente");
    }
}
