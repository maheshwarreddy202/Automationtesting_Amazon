package Amazon_Login;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.*;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

public class BaseClass {

	protected WebDriver driver;
	private static final String SECRET_KEY = "";
	private static Properties prop;

	@BeforeMethod
	public void setup() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.amazon.in/");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		readProp();
		driver.findElement(By.xpath("(//div[@class=\"nav-div\"])[2]")).click();
		driver.findElement(By.id("ap_email_login")).sendKeys(decrypt(prop.getProperty("Email")));
		driver.findElement(By.xpath("//input[@type=\"submit\"]")).click();
		driver.findElement(By.id("ap_password")).sendKeys(decrypt(prop.getProperty("password")));
		driver.findElement(By.id("signInSubmit")).click();
	}

	@AfterMethod
	public void tearDown() {

		WebElement element = driver.findElement(By.xpath("(//div[@class=\"nav-div\"])[2]"));
		Actions actions = new Actions(driver);
		actions.moveToElement(element).perform();
		driver.findElement(By.xpath("//a[@id=\"nav-item-signout\"]")).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.quit();
	}

	public static Properties readProp() {
		prop = new Properties();
		try {
			FileInputStream ip = new FileInputStream("./src/test/resources/config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop;
	}

	public static String decrypt(String strToDecrypt) {
		try {
			SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
