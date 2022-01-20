package PageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Step1Page {

    private final WebDriver driver;
    private WebDriverWait wait;
    private static Logger log = LogManager.getLogger();

    private By firstName_txtField = By.xpath("//input[@id='firstName']");
    private By lastName_txtField = By.xpath("//input[@id='lastName']");
    private By email_txtField = By.xpath("//input[@id='email']");
    private By emailError_label = By.xpath("//span[@id='emailError']");
    private By birthMonth_selField = By.xpath("//select[@id='birthMonth']");
    private By birthDay_selField = By.xpath("//select[@id='birthDay']");
    private By birthYear_selField = By.xpath("//select[@id='birthYear']");
    private By step1Next_button = By.xpath("//span[normalize-space()='Next: Location']");

    public Step1Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(this.driver, 10L);
    }

    public Step1Page enterFirstName(String firstName) {
        wait.until(ExpectedConditions.presenceOfElementLocated(firstName_txtField)).sendKeys(firstName);
        return this;
    }

    public Step1Page enterLastName(String lastName) {
        wait.until(ExpectedConditions.presenceOfElementLocated(lastName_txtField)).sendKeys(lastName);
        return this;
    }

    public Step1Page enterEmail(String email) {
        wait.until(ExpectedConditions.presenceOfElementLocated(email_txtField)).sendKeys(email);
        return this;
    }

    public Step1Page enterBirthMonth(String monthValue) {
        Select sMonth = new Select(driver.findElement(birthMonth_selField));
        sMonth.selectByValue(monthValue);
        return this;
    }

    public Step1Page enterBirthDay(String dayValue) {
        Select sMonth = new Select(driver.findElement(birthDay_selField));
        sMonth.selectByValue(dayValue);
        return this;
    }

    public Step1Page enterBirthYear(String yearValue) {
        Select sMonth = new Select(driver.findElement(birthYear_selField));
        sMonth.selectByValue(yearValue);
        return this;
    }

    public void clickStep1NextButton() {
        wait.until(ExpectedConditions.presenceOfElementLocated(step1Next_button)).click();
    }

    public void accessSignUpPersonalPage(String url) {
        log.info("Go to Step1 page");
        driver.get(url);
    }

    public void fillStep1Values(String firstName, String lastName, String email,
                                String month, String day, String year) throws InterruptedException {
        log.info("Enter value for Step 1");
        this.enterFirstName(firstName)
                .enterLastName(lastName)
                .enterEmail(email)
                .enterBirthMonth(month)
                .enterBirthDay(day)
                .enterBirthYear(year);

        Thread.sleep(1000);
        this.clickStep1NextButton();
    }

    public boolean isEmailInvalid() {
        try {
            driver.findElement(emailError_label);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessage(){
        WebElement el = driver.findElement(emailError_label);
        return el.getText();
    }
}
