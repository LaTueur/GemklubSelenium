import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.remote.*;

import com.mailslurp.clients.*;

import java.net.MalformedURLException;
import java.net.URL;

public class GemklubTest {
    private WebDriver driver;
    private Config config;
    private MainPage mainPage;

    @BeforeEach
    public void setup()  throws MalformedURLException  {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        config = new Config();

        mainPage = new MainPage(driver);
        mainPage.navigateTo();

        Cookie cookie1 = new Cookie("auroraMarketingCookieAccepted", "1");
        Cookie cookie2 = new Cookie("auroraNanobarAccepted", "1");
        driver.manage().addCookie(cookie1);
        driver.manage().addCookie(cookie2);

        driver.navigate().refresh();
    }

    @Test
    public void testLoginFromMainPage(){
        LoginPage loginPage = mainPage.navigateToLogin();

        ProfilePage profilePage = loginPage.fillCredentialAndLogIn(config.getEmail(), config.getPassword());

        profilePage.waitOnPage();
        assertEquals("Fiókom", profilePage.getMainHeaderText());
    }

    @Test
    public void testLogout() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();

        ProfilePage profilePage = loginPage.fillCredentialAndLogIn(config.getEmail(), config.getPassword());

        profilePage.waitOnPage();

        profilePage.logOut();
        loginPage.waitOnPage();
    }

    @Test
    public void testLoginWrongPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();

        loginPage.randomLogIn(config.getEmail());

        assertEquals("Hibás felhasználónév és/vagy jelszó.", loginPage.getAlert());
    }

    @Test
    public void testLoginWrongCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();

        loginPage.randomLogIn();

        assertEquals("Hibás felhasználónév és/vagy jelszó.", loginPage.getAlert());
    }

    @Test
    public void testRegistration() throws ApiException {
        RegistrationPage regPage = new RegistrationPage(driver);
        regPage.navigateTo();

        MailManager mailManager = new MailManager(config.getApiKey());

        SuccessPage succPage = regPage.fillCredentialAndRegister(mailManager.getEmailAddress(), "VerySecretPassword", "Csiga", "Biga", "06705555555");
        
        succPage.waitOnPage();
        assertTrue(succPage.getBodyText().contains("Sikeres regisztráció"));

        assertEquals("Jó, hogy itt vagy!", mailManager.lastestEmailSubject());
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

        assertEquals(gamePage.getProductName(), productName);
        assertEquals(gamePage.getPlayerCount(), playerCount);
        assertEquals(gamePage.getAgeCategory(), ageCategory);
        assertEquals(gamePage.getGameTime(), gameTime);
    }
    
    
    @AfterEach
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
