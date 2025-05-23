import org.openqa.selenium.*;

class RegistrationPage extends GemklubBase {
    private By emailBy = By.xpath("//input[@id='email']");
    private By passwordBy = By.xpath("//input[@id='password']");
    private By firstnameBy = By.xpath("//input[@id='firstname']");
    private By lastnameBy = By.xpath("//input[@id='lastname']");
    private By telephoneBy = By.xpath("//input[@id='telephone']");
    private By registrationCheckBy = By.xpath("//label[@for='registrationCheck_id']");
    private By registrationButtonBy = By.xpath("//div[@class='form-group']/button");

    public RegistrationPage(WebDriver driver){
        super(driver, "customer/register");
    }

    public SuccessPage fillCredentialAndRegister(String email, String password, String firstname, String lastname, String telephone) {
        waitAndReturnElement(emailBy).sendKeys(email);
        waitAndReturnElement(passwordBy).sendKeys(password);
        waitAndReturnElement(firstnameBy).sendKeys(firstname);
        waitAndReturnElement(lastnameBy).sendKeys(lastname);
        waitAndReturnElement(telephoneBy).sendKeys(telephone);

        waitAndReturnElement(registrationCheckBy).click();
        waitAndReturnElement(registrationButtonBy).click();

        return new SuccessPage(driver);
    }
}