import org.openqa.selenium.*;

class GemklubBase extends PageBase {
    private By loginNavigationBy = By.xpath("//a[@title='Belépés']");
    private By profileBy = By.xpath("//li[@class='dropdown logged-dropdown']");
    private By logoutBy = By.xpath("//a[@title='Kilépés']");

    protected GemklubBase(WebDriver driver, String urlEnd, String title) {
        super(driver, "https://www.gemklub.hu/" + urlEnd, title);
    }
    protected GemklubBase(WebDriver driver, String urlEnd) {
        this(driver, urlEnd, "Gémklub");
    }

    public LoginPage navigateToLogin() {
        waitAndReturnElement(loginNavigationBy).click();
        return new LoginPage(driver);
    }

    public void logOut(){
        waitAndReturnElement(profileBy).moveToElement();
        waitAndReturnElement(logoutBy).click();
    }
}