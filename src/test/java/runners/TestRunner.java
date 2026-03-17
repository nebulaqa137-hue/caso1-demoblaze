package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * TestRunner - Ejecuta Cucumber con TestNG
 * Configura features, glue, plugins de reporte
 */
@CucumberOptions(
    features  = "src/test/resources/features",
    glue      = {"hooks", "stepdefinitions"},
    plugin    = {
        "pretty",
        "html:reports/cucumber-report.html",
        "json:reports/cucumber-report.json",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
    },
    monochrome = true,
    tags = "@smoke or @regression or @e2e or @login or @carrito or @compra"
)
public class TestRunner extends AbstractTestNGCucumberTests {
    // TestNG ejecuta los scenarios via AbstractTestNGCucumberTests
}
