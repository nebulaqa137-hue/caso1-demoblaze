package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import utils.ConfigManager;
import utils.DriverFactory;
import utils.ScreenshotHelper;

/**
 * Hooks - Ciclo de vida de cada escenario Cucumber
 * Before: inicializa driver y navega a URL base
 * After:  toma screenshot si falla, cierra driver
 */
public class Hooks {

    private static final Logger log = LogManager.getLogger(Hooks.class);
    private static final ConfigManager config = ConfigManager.getInstance();

    @Before
    public void setUp(Scenario scenario) {
        log.info("========================================");
        log.info("INICIO ESCENARIO: " + scenario.getName());
        log.info("========================================");
        DriverFactory.initDriver(config.getBrowser());
        DriverFactory.getDriver().get(config.getUrl());
        log.info("Navegando a: " + config.getUrl());
    }

    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverFactory.getDriver();
        if (scenario.isFailed()) {
            log.warn("ESCENARIO FALLIDO: " + scenario.getName() + " - Tomando screenshot...");
            byte[] screenshot = ScreenshotHelper.takeScreenshotAsBytes(driver);
            scenario.attach(screenshot, "image/png", "Screenshot - " + scenario.getName());
        }
        log.info("========================================");
        log.info("FIN ESCENARIO: " + scenario.getName() + " | STATUS: " + scenario.getStatus());
        log.info("========================================");
        DriverFactory.quitDriver();
    }
}
