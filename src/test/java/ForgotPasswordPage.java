import org.openqa.selenium.*;

class ForgotPasswordPage extends GemklubBase {
    private By emailBy = By.xpath("//input[@id='inputEmail']");
    private By submitButtonBy = By.xpath("//button[@type='submit']");

    public ForgotPasswordPage(WebDriver driver) {
        super(driver, "index.php?route=account/forgotten", "Elfelejtette");
    }

    public LoginPage fillCredentialAndRequestPassword(String email) {
        waitAndReturnElement(emailBy).sendKeys(email);

        waitAndReturnElement(submitButtonBy).click();

        return new LoginPage(driver);
    }
}