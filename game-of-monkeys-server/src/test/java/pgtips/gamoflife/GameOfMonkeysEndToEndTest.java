package pgtips.gamoflife;

import com.codeborne.selenide.WebDriverRunner;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import pgtips.gameoflife.GameOfMonkeys;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static pgtips.gamoflife.GameOfMonkeysEndToEndTest.EmptyCageMatcher.hasEmptyBeds;
import static pgtips.gamoflife.GameOfMonkeysEndToEndTest.MonkeyInCageMatcher.hasMonkeyAt;

public class GameOfMonkeysEndToEndTest {

    private WebDriver driver;
    private GameOfMonkeys gameOfMonkeys;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/Georgina/Downloads/chromedriver");
        driver = new ChromeDriver();
        WebDriverRunner.setWebDriver(driver);
    }

    @Test
    public void generateCageWithEmptyBeds() throws Exception {
        gameOfMonkeys = new GameOfMonkeys(0);
        gameOfMonkeys.start();

        driver.get("http://localhost:5000/zoo");

        WebElement actualCage = driver.findElement(By.className("cage"));


        assertThat(actualCage, hasEmptyBeds());
    }

    @Test
    public void generateCageWithAMonkey() throws Exception {
        gameOfMonkeys = new GameOfMonkeys(1);
        gameOfMonkeys.start();

        driver.get("http://localhost:5000/zoo");

        WebElement actualCage = driver.findElement(By.className("cage"));


        assertThat(actualCage, hasMonkeyAt(0, 0));
    }

    public static class MonkeyInCageMatcher extends TypeSafeDiagnosingMatcher<WebElement> {
        private final int x;
        private final int y;

        public MonkeyInCageMatcher(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public static Matcher<WebElement> hasMonkeyAt(int x, int y) {
            return new MonkeyInCageMatcher(x, y);
        }

        @Override
        protected boolean matchesSafely(WebElement actualCage, Description mismatchDescription) {
            WebElement expectedMonkey = actualCage.findElement(By.className("bed-" + x + "-" + y));
            if (expectedMonkey.getAttribute("class").equals("bed empty-bed")) {
                return false;
            }

            mismatchDescription
                    .appendText("found empty bed at location ")
                    .appendValue(x)
                    .appendText("-")
                    .appendValue(y);

            return true;
        }

        @Override
        public void describeTo(Description description) {

        }
    }

    public static class EmptyCageMatcher extends TypeSafeDiagnosingMatcher<WebElement> {

        public static Matcher<WebElement> hasEmptyBeds() {
            return new EmptyCageMatcher();
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
    public void tearDown() throws InterruptedException {
        gameOfMonkeys.stop();
        driver.close();
    }
}
