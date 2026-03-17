package stepdefinitions;

import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.ProductPage;
import utils.ConfigManager;
import utils.DriverFactory;

/**
 * StepDefinitions - Carrito y Compra
 */
public class CarritoSteps {

    private ProductPage productPage;
    private static final ConfigManager config = ConfigManager.getInstance();

    public CarritoSteps() {
        this.productPage = new ProductPage(DriverFactory.getDriver());
    }

    @When("el usuario selecciona el primer producto")
    public void elUsuarioSeleccionaElPrimerProducto() {
        productPage = new ProductPage(DriverFactory.getDriver());
        productPage.clickFirstProduct();
    }

    @When("el usuario selecciona el producto {string}")
    public void elUsuarioSeleccionaElProducto(String nombre) {
        productPage = new ProductPage(DriverFactory.getDriver());
        productPage.clickProductByName(nombre);
    }

    @And("agrega el producto al carrito")
    public void agregaElProductoAlCarrito() {
        productPage.addToCart();
    }

    @And("navega al carrito")
    public void navegaAlCarrito() {
        productPage.goToCart();
    }

    @Then("el carrito debe tener al menos {int} producto")
    public void elCarritoDebeTenerAlMenos(int cantidad) {
        int count = productPage.getCartItemCount();
        Assert.assertTrue(count >= cantidad,
            "El carrito debe tener al menos " + cantidad + " producto(s). Encontrado: " + count);
    }

    @Then("el producto {string} debe estar en el carrito")
    public void elProductoDebeEstarEnElCarrito(String nombre) {
        Assert.assertTrue(productPage.isProductInCart(nombre),
            "El producto '" + nombre + "' debe estar en el carrito");
    }

    @And("elimina todos los productos del carrito")
    public void eliminaTodosLosProductosDelCarrito() {
        productPage.deleteAllCartItems();
    }

    @Then("el carrito debe estar vacio")
    public void elCarritoDebeEstarVacio() {
        Assert.assertEquals(productPage.getCartItemCount(), 0,
            "El carrito debe estar vacio");
    }

    @And("completa la orden de compra")
    public void completaLaOrdenDeCompra() {
        productPage.placeOrder(
            config.getBuyerName(),
            config.getBuyerCountry(),
            config.getBuyerCity(),
            config.getBuyerCard(),
            config.getBuyerMonth(),
            config.getBuyerYear()
        );
    }

    @Then("debe ver la confirmacion de compra")
    public void debeVerLaConfirmacionDeCompra() {
        Assert.assertTrue(productPage.isPurchaseConfirmed(),
            "Debe mostrarse confirmacion de compra 'Thank you'");
        productPage.acceptPurchaseConfirmation();
    }
}
