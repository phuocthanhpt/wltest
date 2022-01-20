package PageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WeatherMapHomePage {

    private final WebDriver driver;
    private WebDriverWait wait;
    private static Logger log = LogManager.getLogger();

    public By apiKeys_hyperLink = By.xpath("//a[normalize-space()='API keys']");

    public WeatherMapHomePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(this.driver, 10L);
    }


    public void goToAPIKeysScreen(){
        wait.until(ExpectedConditions.presenceOfElementLocated(apiKeys_hyperLink)).click();
    }
}
