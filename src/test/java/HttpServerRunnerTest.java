import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HttpServerRunnerTest {

    @Test
    public void portParameter() {
        HttpServerRunner httpServerRunner = new HttpServerRunner();
        String[] commandLineArguments = new String[]{"-p", "8080"};
        int port = httpServerRunner.extractPort(commandLineArguments);

        assertThat(port, is(8080));
    }

    @Test
    public void defaultsPortTo5000IfInvalidValueProvided() {
        HttpServerRunner httpServerRunner = new HttpServerRunner();
        String commandLineArguments = "-p banana";
        int port = httpServerRunner.extractPort(commandLineArguments);

        assertThat(port, is(5000));
    }

    @Test
    public void defaultHostIsSetTo5000() {
        HttpServerRunner httpServerRunner = new HttpServerRunner();
        String[] commandLineArguments = new String[]{};
        int port = httpServerRunner.extractPort(commandLineArguments);

        assertThat(port, is(5000));
    }

    @Test
    public void publicDirectoryParameter() {
        HttpServerRunner httpServerRunner = new HttpServerRunner();
        String[] commandLineArguments = new String[]{"-d", "/some/path/here"};
        String publicDirectory = httpServerRunner.extractPublicDirectory(commandLineArguments);

        assertThat(publicDirectory, is("/some/path/here"));
    }
}
