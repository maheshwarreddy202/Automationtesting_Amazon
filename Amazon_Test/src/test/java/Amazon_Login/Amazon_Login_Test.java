package Amazon_Login;

import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class Amazon_Login_Test extends BaseClass {

	@Test
	public void productSearch() {

		WebDriver driver = this.driver; 
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("Pen box");
		driver.findElement(By.xpath("//input[@id=\"nav-search-submit-button\"]")).click();
		driver.findElement(By.xpath("(//h2[@class=\"a-size-base-plus a-spacing-none a-color-base a-text-normal\"])[4]")).click();

		String parentWindow = driver.getWindowHandle(); 
		Set<String> allWindows = driver.getWindowHandles();

		for (String CH : allWindows) {
			if (!CH.equals(parentWindow)) {
				driver.switchTo().window(CH);
				List<WebElement> qtyDropdowns = driver.findElements(By.xpath("//span[@id=\"a-autoid-0-announce\"]"));

				if (!qtyDropdowns.isEmpty()) {
					WebElement quantityDropdown = qtyDropdowns.get(0);
					Select select = new Select(quantityDropdown);
					select.selectByVisibleText("4");
					System.out.println("Quantity increased.");
					driver.findElement(By.xpath("//input[@id=\"add-to-cart-button\"]")).click();
				} else {
					System.out.println("Only one Quantity available. Proceeding with default quantity.");
					driver.findElement(By.xpath("//input[@id=\"add-to-cart-button\"]")).click();
				}
				driver.close();
			}

		}

		driver.switchTo().window(parentWindow);
		driver.navigate().refresh();
		driver.findElement(By.xpath("//div[@id=\"nav-cart-count-container\"]")).click();
		driver.findElement(By.xpath("//input[@name=\"proceedToRetailCheckout\"]")).click();
		driver.navigate().to("https://www.amazon.in/");

	}

}
