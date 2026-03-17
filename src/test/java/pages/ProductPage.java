package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * ProductPage - POM con Page Factory
 * Contiene locators y acciones de productos, carrito y checkout
 */
public class ProductPage extends BasePage {

    // ==================== PAGE FACTORY LOCATORS ====================
    @FindBy(css = ".card-title a")
    private List<WebElement> productCards;

    @FindBy(xpath = "//a[text()='Add to cart']")
    private WebElement btnAddToCart;

    @FindBy(id = "cartur")
    private WebElement btnCart;

    @FindBy(css = "#tbodyid tr")
    private List<WebElement> cartItems;

    @FindBy(xpath = "//button[text()='Place Order']")
    private WebElement btnPlaceOrder;

    @FindBy(id = "name")
    private WebElement inputName;

    @FindBy(id = "country")
    private WebElement inputCountry;

    @FindBy(id = "city")
    private WebElement inputCity;

    @FindBy(id = "card")
    private WebElement inputCard;

    @FindBy(id = "month")
    private WebElement inputMonth;

    @FindBy(id = "year")
    private WebElement inputYear;

    @FindBy(xpath = "//button[text()='Purchase']")
    private WebElement btnPurchase;

    @FindBy(css = ".sweet-alert h2")
    private WebElement confirmationTitle;

    @FindBy(css = ".confirm")
    private WebElement btnOkConfirm;

    // ==================== CONSTRUCTOR ====================
    public ProductPage(WebDriver driver) {
        super(driver);
    }

    // ==================== ACCIONES ====================
    public void clickFirstProduct() {
        log.info("Click en primer producto");
        wait.waitForPresence(By.cssSelector(".card-title a")).get(0).click();
    }

    public void clickProductByName(String name) {
        log.info("Click en producto: " + name);
        actions.scrollToBottom();
        wait.waitForClickable(By.linkText(name)).click();
    }

    public void addToCart() {
        log.info("Agregando al carrito");
        wait.waitForClickable(By.xpath("//a[text()='Add to cart']")).click();
        if (wait.waitForAlert()) {
            Alert alert = driver.switchTo().alert();
            log.info("Alert: " + alert.getText());
            alert.accept();
        }
        wait.waitSeconds(1);
    }

    public void goToCart() {
        log.info("Navegando al carrito");
        wait.waitForClickable(By.id("cartur")).click();
        wait.waitSeconds(2);
    }

    public int getCartItemCount() {
        try {
            List<WebElement> items = wait.waitForPresence(By.cssSelector("#tbodyid tr"));
            log.info("Items en carrito: " + items.size());
            return items.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean isProductInCart(String productName) {
        List<WebElement> names = driver.findElements(
            By.cssSelector("#tbodyid tr td:nth-child(2)"));
        return names.stream().anyMatch(e -> e.getText().equalsIgnoreCase(productName));
    }

    public void placeOrder(String name, String country, String city,
                           String card, String month, String year) {
        log.info("Completando orden de compra");
        wait.waitForClickable(By.xpath("//button[text()='Place Order']")).click();
        wait.waitForVisible(By.id("name")).sendKeys(name);
        inputCountry.sendKeys(country);
        inputCity.sendKeys(city);
        inputCard.sendKeys(card);
        inputMonth.sendKeys(month);
        inputYear.sendKeys(year);
        btnPurchase.click();
        wait.waitSeconds(2);
    }

    public boolean isPurchaseConfirmed() {
        try {
            WebElement confirmation = wait.waitForVisible(By.cssSelector(".sweet-alert h2"));
            log.info("Confirmacion: " + confirmation.getText());
            return confirmation.getText().toLowerCase().contains("thank you");
        } catch (Exception e) {
            log.error("No se encontro confirmacion de compra");
            return false;
        }
    }

    public void acceptPurchaseConfirmation() {
        wait.waitForClickable(By.cssSelector(".confirm")).click();
    }

    public void deleteAllCartItems() {
        List<WebElement> deleteBtns = driver.findElements(By.xpath("//a[text()='Delete']"));
        for (WebElement btn : deleteBtns) {
            btn.click();
            wait.waitSeconds(1);
        }
        log.info("Todos los items eliminados");
    }
}
