package PageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Step2Page {
    private final WebDriver driver;
    private WebDriverWait wait;
    private static Logger log = LogManager.getLogger();

    private By step2_title = By.xpath("//span[@class='sub-title']");

    public Step2Page(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(this.driver, 10L);
    }

    public String getStep2Title(){
        return wait.until(ExpectedConditions.presenceOfElementLocated(step2_title)).getText();
    }
}
