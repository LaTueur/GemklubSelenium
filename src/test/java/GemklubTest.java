import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.remote.*;

import com.mailslurp.clients.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.*;

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
    @DisplayName("User can navigate to the login page and login.")
    public void testLoginFromMainPage(){
        LoginPage loginPage = mainPage.navigateToLogin();

        ProfilePage profilePage = loginPage.fillCredentialAndLogIn(config.getEmail(), config.getPassword());

        profilePage.waitOnPage();
        assertEquals("Fiókom", profilePage.getMainHeaderText());
    }

    @Test
    @DisplayName("User can login and logout.")
    public void testLogout() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();

        ProfilePage profilePage = loginPage.fillCredentialAndLogIn(config.getEmail(), config.getPassword());

        profilePage.waitOnPage();

        profilePage.logOut();
        loginPage.waitOnPage();
    }

    @Test
    @DisplayName("User is notified when using wrong password.")
    public void testLoginWrongPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();

        loginPage.randomLogIn(config.getEmail());

        assertEquals("Hibás felhasználónév és/vagy jelszó.", loginPage.getAlert());
    }

    @Test
    @DisplayName("User is notified when using wrong email and password.")
    public void testLoginWrongCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();

        loginPage.randomLogIn();

        assertEquals("Hibás felhasználónév és/vagy jelszó.", loginPage.getAlert());
    }

    @Test
    @DisplayName("User can register and get an email notification about it.")
    public void testRegistration() throws ApiException {
        RegistrationPage regPage = new RegistrationPage(driver);
        regPage.navigateTo();

        MailManager mailManager = new MailManager(config.getApiKey());

        SuccessPage succPage = regPage.fillCredentialAndRegister(mailManager.getEmailAddress(), "VerySecretPassword", "Csiga", "Biga", "06705555555");
        
        succPage.waitOnPage();
        assertTrue(succPage.getBodyText().contains("Sikeres regisztráció"));
        
        mailManager.waitForLatestEmail();
        assertEquals("Jó, hogy itt vagy!", mailManager.getLatestEmailSubject());
    }

    @Test
    @DisplayName("User can use the forgot password feature and gets a new password and can login with that.")
    public void testForgotPassword() throws ApiException {
        ForgotPasswordPage forgotPage = new ForgotPasswordPage(driver);
        forgotPage.navigateTo();

        MailManager mailManager = new MailManager(config.getApiKey(), config.getInboxId());

        LoginPage loginPage = forgotPage.fillCredentialAndRequestPassword(mailManager.getEmailAddress());

        loginPage.waitOnPage();

        mailManager.waitForLatestEmail();
        assertEquals("Gémklub – A játék élmény! - Új jelszó", mailManager.getLatestEmailSubject());
        
        Pattern pattern = Pattern.compile("Az új jelszó:\\s*(\\w+)");
        Matcher matcher = pattern.matcher(mailManager.getLatestEmailBody());

        assertTrue(matcher.find());

        String password = matcher.group(1);
        ProfilePage profilePage = loginPage.fillCredentialAndLogIn(mailManager.getEmailAddress(), password);

        profilePage.waitOnPage();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "games.csv", numLinesToSkip = 1)
    @DisplayName("User can view games and the correct info is displayed.")
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
