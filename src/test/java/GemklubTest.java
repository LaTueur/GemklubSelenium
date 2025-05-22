import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.*;

import java.net.MalformedURLException;
import java.net.URL;

public class GemklubTest {
    public WebDriver driver;
    public Config config;
    
    @BeforeEach
    public void setup()  throws MalformedURLException  {
        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        driver.manage().window().maximize();
        config = new Config();
    }
    
    @Test
    public void testLoginFromMainPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.navigateTo();
        LoginPage loginPage = mainPage.navigateToLogin();

        loginPage.fillCredentialAndLogIn(config.getEmail(), config.getPassword());

        Assert.assertTrue(loginPage.getBodyText().contains("Fiókom"));
    }

    @Test
    public void testLogout() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();

        loginPage.fillCredentialAndLogIn(config.getEmail(), config.getPassword());

        loginPage.logOut();
    }

    @Test
    public void testLoginWrongPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();

        loginPage.randomLogIn(config.getEmail());

        Assert.assertTrue(loginPage.getBodyText().contains("Hibás felhasználónév és/vagy jelszó."));
    }

    @Test
    public void testLoginWrongCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();

        loginPage.randomLogIn();

        Assert.assertTrue(loginPage.getBodyText().contains("Hibás felhasználónév és/vagy jelszó."));
    }

    @ParameterizedTest
    @CsvSource({
        "kontroll-13011, Kontroll, 2 fő, 20-25 perc, 14+",
        "aranyfolyo-12974, Aranyfolyó, 2-4 fő, 60-90 perc, 14+",
        "concept-85, Concept, 4-12 fő, 40 perc, 10+"
    })
    public void testGamePageData(String gameId, String productName, String playerCount, String gameTime, String ageCategory){
        GamePage gamePage = new GamePage(driver, gameId, productName);
        gamePage.navigateTo();

        Assert.assertEquals(gamePage.getProductName(), productName);
        Assert.assertEquals(gamePage.getPlayerCount(), playerCount);
        Assert.assertEquals(gamePage.getAgeCategory(), ageCategory);
        Assert.assertEquals(gamePage.getGameTime(), gameTime);
    }
    
    @AfterEach
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
