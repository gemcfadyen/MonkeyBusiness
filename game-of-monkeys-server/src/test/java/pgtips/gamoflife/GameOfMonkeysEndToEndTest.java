package pgtips.gamoflife;

import com.codeborne.selenide.WebDriverRunner;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static pgtips.gamoflife.GameOfMonkeysEndToEndTest.CageMatcher.hasEmptyBeds;

public class GameOfMonkeysEndToEndTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/Georgina/Downloads/chromedriver");
        driver = new ChromeDriver();
        WebDriverRunner.setWebDriver(driver);
    }

    @Test
    public void generateCageWithEmptyBeds() throws Exception {
        GameOfMonkeys gameOfMonkeys = new GameOfMonkeys();
        gameOfMonkeys.start();

        driver.get("http://localhost:5000/zoo");

        WebElement actualCage = driver.findElement(By.className("cage"));


        assertThat(actualCage, hasEmptyBeds());
    }

    public static class CageMatcher extends TypeSafeDiagnosingMatcher<WebElement> {

        public static Matcher<WebElement> hasEmptyBeds() {
            return new CageMatcher();
        }

        @Override
        protected boolean matchesSafely(WebElement actualCage, Description mismatchDescription) {
            List<WebElement> expectedMonkeyText = actualCage.findElements(By.className("bed"));
            assertThat(expectedMonkeyText.size(), is(100));
            int notEmptyBeds = 0;
            for (WebElement webElement : expectedMonkeyText) {
                if (!webElement.getAttribute("class").equals("bed empty-bed")) {
                    notEmptyBeds++;
                }
            }
            mismatchDescription.appendText("found ").appendValue(notEmptyBeds).appendText(" not empty beds");
            return notEmptyBeds == 0;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("a cage of empty beds");
        }
    }


    @After
    public void tearDown() {
        driver.close();

    }
}
