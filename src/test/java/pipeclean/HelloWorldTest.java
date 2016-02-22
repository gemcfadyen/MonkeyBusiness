package pipeclean;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HelloWorldTest {

    @Test
    public void presentsGreeting() {
        HelloWorld helloWorld = new HelloWorld();
        assertThat(helloWorld.greeting(), is("Hiiii"));
    }
}
