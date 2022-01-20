package PageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class APIKeysPage {

    private final WebDriver driver;
    private WebDriverWait wait;
    private static Logger log = LogManager.getLogger();

    public By inputKey_textField = By.xpath("//input[@id='api_key_form_name']");
    public By generate_button = By.xpath("//input[@name='commit']");



    public APIKeysPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(this.driver, 10L);
    }



}
