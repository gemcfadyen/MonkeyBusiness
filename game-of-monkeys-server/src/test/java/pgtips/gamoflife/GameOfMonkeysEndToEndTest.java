package pgtips.gamoflife;

import com.codeborne.selenide.WebDriverRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class GameOfMonkeysEndToEndTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/Georgina/Downloads/chromedriver");
        driver = new ChromeDriver();
        WebDriverRunner.setWebDriver(driver);
    }

    @Test
    public void generateMonkeys() throws Exception {
        GameOfMonkeys gameOfMonkeys = new GameOfMonkeys();
        gameOfMonkeys.start();

        driver.get("http://localhost:5000/zoo");
        Thread.sleep(5000);

        WebElement monkeyElement = driver.findElement(By.className("qa-monkey"));

        String expectedMonkeyText = monkeyElement.getText();
        assertThat(expectedMonkeyText, is("Mun-key!"));
    }

    @After
    public void tearDown() {
        driver.close();

    }
}
