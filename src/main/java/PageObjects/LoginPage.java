package PageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private final WebDriver driver;
    private WebDriverWait wait;
    private static Logger log = LogManager.getLogger();

    public By username_textField = By.xpath("//div[@class='input-group']//input[@id='user_email']");
    public By password_textField = By.xpath("//input[@id='user_password']");
    public By submit_button = By.xpath("//input[@value='Submit']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(this.driver, 10L);
    }

    public LoginPage enterUsername(String username){
        wait.until(ExpectedConditions.presenceOfElementLocated(username_textField)).sendKeys(username);
        return this;
    }

    public LoginPage enterPassword(String password){
        wait.until(ExpectedConditions.presenceOfElementLocated(password_textField)).sendKeys(password);
        return this;
    }

    public void clickSubmitButton(){
        wait.until(ExpectedConditions.elementToBeClickable(submit_button)).click();
    }

    public void doLogin(String username, String password){
        this.enterUsername(username)
                .enterPassword(password)
                .clickSubmitButton();
    }

    public void accessLoginPage(String url) {
        log.info("accessLoginPage");
        driver.get(url);
    }
}
