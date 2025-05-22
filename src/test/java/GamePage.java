import org.openqa.selenium.*;

class GamePage extends GemklubBase {
    private By productNameBy = By.xpath("//span[@class='product-page-product-name']");

    public GamePage(WebDriver driver, String gameId, String title) {
        super(driver, gameId, title);
    }

    public String getProductName(){
        return waitAndReturnElement(productNameBy).getText();
    }

    public String getPlayerCount(){
        return nthCustomerInfo(1).getText();
    }

    public String getGameTime(){
        return nthCustomerInfo(2).getText();
    }

    public String getAgeCategory(){
        return nthCustomerInfo(3).getText();
    }

    private By nthCustomerInfo(int n){
        return By.xpath("//div[class='custom-parameter-icon-wrapper']/td[position()=" + n + "]/span");
    }
}