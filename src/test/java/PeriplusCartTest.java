import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;

public class PeriplusCartTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Test
    public void addToCartTest() throws InterruptedException {
        // 1. Open Priplus
        driver.get("https://www.periplus.com/account/Login");
        System.out.println("Opened Periplus login page");
        Thread.sleep(3000);

        // 2. Input email stage, if u want try don't forget to change the email and password
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='email']")));
        WebElement emailField = driver.findElement(By.cssSelector("input[type='email']"));
        emailField.click();
        emailField.clear();
        emailField.sendKeys("tryemail@gmail.com");  //here to change email
        System.out.println("Email entered");

        // 3. Input password, please change password
        WebElement passField = driver.findElement(By.cssSelector("input[type='password']"));
        passField.click();
        passField.clear();
        passField.sendKeys("CHANGEHEREYA"); //here to change password
        System.out.println("Password entered");

        // 4. Click login
        driver.findElement(By.id("button-login")).click();
        System.out.println("Login clicked");
        Thread.sleep(4000);

        // 5. Search product
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='filter_name']")));
        driver.findElement(By.cssSelector("input[name='filter_name']")).sendKeys("Luna lovegood");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        System.out.println("Searched for Luna lovegood");
        Thread.sleep(3000);

        // Click first product
        WebElement product = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".single-product .product-img a")));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", product);
        System.out.println("Clicked first product");
        Thread.sleep(3000);

        // 7. Add to cart
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn-add-to-cart")));
        driver.findElement(By.cssSelector(".btn-add-to-cart")).click();
        System.out.println("Added to cart");
        Thread.sleep(2000);

        // 8. Verification cart
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cart_total")));
        WebElement cartTotal = driver.findElement(By.id("cart_total"));
        Assert.assertNotNull(cartTotal);
        System.out.println("SUCCESS: Product added to cart! Cart total: " + cartTotal.getText());
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}