import org.openqa.selenium.*;

class ProfilePage extends GemklubBase {
    public ProfilePage(WebDriver driver) {
        super(driver, "index.php?route=account/account", "Fiókom");
    }
    
    public String getMainHeaderText() {
        WebElement header = this.waitAndReturnElement(By.tagName("h1"));
        return header.getText();
    }
}