import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

class PageBase {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String url;
    protected String title;    
    
    protected PageBase(WebDriver driver, String url, String title) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.url = url;
        this.title = title;
    }

    public void navigateTo() {
        driver.get(url);
        waitMatchingTitle();
    }

    public void waitMatchingTitle() {
        wait.until(ExpectedConditions.titleContains(title));
    }
    
    protected WebElement waitAndReturnElement(By locator) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this.driver.findElement(locator);
    }
}
