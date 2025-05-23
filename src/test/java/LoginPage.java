import org.openqa.selenium.*;

class LoginPage extends GemklubBase {
    private By emailBy = By.xpath("//input[@id='email_login']");
    private By passwordBy = By.xpath("//input[@id='password_login']");
    private By loginButtonBy = By.xpath("//div[@class='form-group']/button");
    private By alertBy = By.xpath("//div[@class='alert alert-danger']");
    private RandomGenerator randomGenerator = new RandomGenerator();

    public LoginPage(WebDriver driver) {
        super(driver, "customer/login");
    }

    public ProfilePage fillCredentialAndLogIn(String email, String password) {
        WebElement emailElement = waitAndReturnElement(emailBy);
        emailElement.sendKeys(email);

        WebElement passwordElement = waitAndReturnElement(passwordBy);
        passwordElement.sendKeys(password);

        WebElement buttonElement = waitAndReturnElement(loginButtonBy);
        buttonElement.click();

        return new ProfilePage(driver);
    }

    public void randomLogIn() {
        randomLogIn(randomGenerator.randomEmail());
    }

    public void randomLogIn(String email) {
        fillCredentialAndLogIn(email, randomGenerator.randomPassword());
    }

    public String getAlert() {
        return waitAndReturnElement(alertBy).getText();
    }
}