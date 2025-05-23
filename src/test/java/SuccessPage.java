import org.openqa.selenium.*;

class SuccessPage extends GemklubBase {
    public SuccessPage(WebDriver driver) {
        super(driver, "index.php?route=account/success", "Regisztrációd elkészült!");
    }
}