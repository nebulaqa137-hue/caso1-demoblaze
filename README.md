[README-caso1-demoblaze.md](https://github.com/user-attachments/files/26058338/README-caso1-demoblaze.md)
# Caso 1 - Demoblaze Store Automation

## Descripción

Proyecto de automatización de pruebas UI para el sitio [Demoblaze Store](https://www.demoblaze.com), 

Cubre los flujos principales de la tienda: autenticación de usuarios, gestión del carrito de compras y proceso de compra completo. La arquitectura está basada en Page Object Model con Page Factory y BDD usando Gherkin con Cucumber.

---

## Instalación

**Prerequisitos**

- Java 21
- Maven 3.8 o superior
- Google Chrome
- Git

**Pasos**

```bash
git clone https://github.com/tu-usuario/caso1-demoblaze.git
cd caso1-demoblaze
mvn clean install -DskipTests
```

ChromeDriver es gestionado automáticamente por Selenium Manager desde Selenium 4. No se requiere instalación manual del driver.

---

## Uso

Ejecutar la suite completa:

```bash
mvn clean test
```

Ejecutar por etiquetas:

```bash
mvn clean test "-Dcucumber.filter.tags=@smoke"
mvn clean test "-Dcucumber.filter.tags=@regression"
mvn clean test "-Dcucumber.filter.tags=@login"
  mvn clean test "-Dcucumber.filter.tags=@carrito"
mvn clean test "-Dcucumber.filter.tags=@e2e"
```

Desde Eclipse: click derecho en `testng.xml` → Run As → TestNG Suite.

Los reportes se generan en la carpeta `reports/` al finalizar la ejecución.

---

## Configuración

Archivo: `src/test/resources/config.properties`

```properties
url=https://www.demoblaze.com
browser=chrome
implicit.wait=10
explicit.wait=15
page.load.timeout=30

valid.user=testuser_envioclick
valid.pass=Test1234!
invalid.user=usuario_falso_xyz
invalid.pass=wrongpass

buyer.name=John QA
buyer.country=Mexico
buyer.city=CDMX
buyer.card=4111111111111111
buyer.month=12
buyer.year=2026
```

El usuario `testuser_envioclick` debe registrarse manualmente en Demoblaze antes de ejecutar los tests de login. El registro se hace desde Sign up en el sitio.

---

## Estructura del Proyecto

```
src/test/java/
  hooks/           - ciclo de vida de los tests
  pages/           - page objects
  stepdefinitions/ - steps de cucumber
  runners/         - configuracion del runner
  utils/           - utilidades comunes

src/test/resources/
  features/        - escenarios gherkin
  config.properties
  testng.xml
```

---

## Estructura del Framework

**Hooks**
Se ejecuta antes y después de cada escenario Cucumber. Inicializa el WebDriver, navega a la URL base y cierra el navegador al terminar. Si el escenario falla, toma un screenshot y lo adjunta al reporte.

**BasePage**
Clase base de la que heredan todos los Page Objects. Inicializa Page Factory sobre el driver recibido y provee acceso a WaitHelper y ActionsHelper para que todas las páginas los usen sin instanciarlos individualmente.

**Page Objects (LoginPage, ProductPage, RegisterPage)**
Cada clase representa una página o sección del sitio. Los locators se declaran con `@FindBy` y los métodos encapsulan las acciones. No contienen lógica de negocio ni assertions, solo interacciones con la UI.

**Step Definitions (LoginSteps, CarritoSteps, RegistroSteps)**
Conectan los pasos escritos en Gherkin con el código Java. Instancian los Page Objects correspondientes y delegan las acciones. Las assertions se hacen aquí con TestNG.

**TestRunner**
Configura `@CucumberOptions` con la ruta de features, el paquete glue y los plugins de reporte. Extiende `AbstractTestNGCucumberTests` para integrar Cucumber con TestNG.

**DriverFactory**
Gestiona la creación y destrucción del WebDriver. Soporta Chrome, Firefox y Edge según lo configurado en `config.properties`. Usa `ThreadLocal` para soportar ejecución paralela.

**ConfigManager**
Singleton que carga `config.properties` una sola vez. Expone getters tipados para cada propiedad: URL, browser, timeouts, credenciales y datos de compra.

**WaitHelper**
Centraliza los waits explícitos. Métodos disponibles: `waitForVisible`, `waitForClickable`, `waitForPresence`, `waitForAlert`. Evita duplicar `WebDriverWait` en cada página.

**ScreenshotHelper**
Captura el estado de la pantalla al momento del fallo. Puede guardar el archivo en disco o retornar los bytes para adjuntarlos directamente al reporte de Cucumber.

**ActionsHelper**
Agrupa interacciones avanzadas: scroll al elemento, scroll al fondo, hover, click via JavaScript y cambio de tab. Se usa cuando el click estándar de Selenium no es suficiente por overlays o elementos fuera del viewport.

**RandomDataHelper**
Genera datos de prueba únicos en cada ejecución usando JavaFaker combinado con timestamp. Se usa principalmente en los escenarios de registro para evitar errores por usuario duplicado.

---

## Casos de Prueba

| Feature | Escenario | Tag |
|---|---|---|
| Login | Login exitoso con credenciales válidas | @smoke |
| Login | Login fallido con credenciales inválidas | @negative |
| Login | Logout exitoso | @smoke |
| Carrito | Agregar producto al carrito | @smoke |
| Carrito | Validar producto específico en carrito | @regression |
| Carrito | Eliminar producto del carrito | @regression |
| Carrito | Completar compra end-to-end | @e2e |
| Registro | Registro exitoso con datos aleatorios | @smoke |
| Registro | Registro y login con mismo usuario generado | @regression |
| Registro | Registro con usuario ya existente | @negative |

---

## Tecnologías Usadas

| Tecnología | Versión |
|---|---|
| Java | 21 |
| Selenium WebDriver | 4.20.0 |
| Cucumber | 7.15.0 |
| TestNG | 7.10.2 |
| ExtentReports | 5.1.1 |
| JavaFaker | 1.0.2 |
| Log4j | 2.23.1 |
| Maven | 3.8+ |

---

## Contribución

```bash
git checkout -b feature/nombre-del-cambio
git commit -m "descripcion del cambio"
git push origin feature/nombre-del-cambio
```

Abrir Pull Request describiendo qué se automatizó y por qué.

---

## Licencia
Proyecto desarrollado como evaluación técnica de QA para Envíoclick. Uso educativo.
