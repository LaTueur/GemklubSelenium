import org.openqa.selenium.*;

class LoginPage extends GemklubBase {
    private By emailBy = By.xpath("//input[@id='email_login']");
    private By passwordBy = By.xpath("//input[@id='password_login']");
    private By loginButtonBy = By.xpath("//div[@class='form-group']/button");
    private RandomGenerator randomGenerator = new RandomGenerator();

    public LoginPage(WebDriver driver) {
        super(driver, "customer/login");
    }

    public void fillCredentialAndLogIn(String email, String password) {
        WebElement emailElement = waitVisibiltyAndFindElement(emailBy);
        emailElement.sendKeys(email);

        WebElement passwordElement = waitVisibiltyAndFindElement(passwordBy);
        passwordElement.sendKeys(password);

        WebElement buttonElement = waitVisibiltyAndFindElement(loginButtonBy);
        buttonElement.click();
    }

    public void randomLogIn() {
        randomLogIn(randomGenerator.randomEmail());
    }

    public void randomLogIn(String email) {
        fillCredentialAndLogIn(email, randomGenerator.randomPassword());
    }
}