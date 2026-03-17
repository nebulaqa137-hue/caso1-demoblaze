package utils;

import com.github.javafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;

/**
 * Utility - RandomDataHelper
 * Genera datos aleatorios para pruebas usando JavaFaker
 * Cada instancia genera datos unicos por ejecucion
 */
public class RandomDataHelper {

    private static final Logger log = LogManager.getLogger(RandomDataHelper.class);
    private static final Faker faker = new Faker(new Locale("en"));

    // Timestamp para garantizar unicidad en cada ejecucion
    private static final long TIMESTAMP = System.currentTimeMillis();

    private RandomDataHelper() {}

    // ---- USUARIO ----
    public static String randomUsername() {
        String user = faker.name().firstName().toLowerCase()
                    + "_" + TIMESTAMP % 10000;
        log.info("Username generado: " + user);
        return user;
    }

    public static String randomPassword() {
        // Demoblaze acepta cualquier contrasena
        return "Pass" + faker.number().digits(4) + "!";
    }

    public static String randomEmail() {
        return faker.internet().emailAddress()
               .replace("@", "_" + TIMESTAMP % 10000 + "@");
    }

    public static String randomFirstName() {
        return faker.name().firstName();
    }

    public static String randomLastName() {
        return faker.name().lastName();
    }

    // ---- DATOS GENERALES ----
    public static String randomPhone() {
        return faker.phoneNumber().cellPhone();
    }

    public static String randomCity() {
        return faker.address().city();
    }

    public static String randomAddress() {
        return faker.address().streetAddress();
    }

    public static String randomPostcode() {
        return faker.address().zipCode().substring(0, 5);
    }

    // Log de todos los datos generados para trazabilidad
    public static void logGeneratedData(String username, String password) {
        log.info("=== DATOS RANDOM GENERADOS ===");
        log.info("Username : " + username);
        log.info("Password : " + password);
        log.info("Timestamp: " + TIMESTAMP);
        log.info("==============================");
    }
}
