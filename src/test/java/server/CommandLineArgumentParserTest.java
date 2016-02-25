package server;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CommandLineArgumentParserTest {

    @Test
    public void portParameter() {
        CommandLineArgumentParser commandLineArgumentParser = new CommandLineArgumentParser();
        String[] commandLineArguments = new String[]{"-p", "8080"};
        int port = commandLineArgumentParser.extractPort(commandLineArguments);

        assertThat(port, is(8080));
    }

    @Test
    public void defaultsPortTo5000IfInvalidValueProvided() {
        CommandLineArgumentParser commandLineArgumentParser = new CommandLineArgumentParser();
        String commandLineArguments = "-p banana";
        int port = commandLineArgumentParser.extractPort(commandLineArguments);

        assertThat(port, is(5000));
    }

    @Test
    public void defaultHostIsSetTo5000() {
        CommandLineArgumentParser commandLineArgumentParser = new CommandLineArgumentParser();
        String[] commandLineArguments = new String[]{};
        int port = commandLineArgumentParser.extractPort(commandLineArguments);

        assertThat(port, is(5000));
    }

    @Test
    public void publicDirectoryParameter() {
        CommandLineArgumentParser commandLineArgumentParser = new CommandLineArgumentParser();
        String[] commandLineArguments = new String[]{"-d", "/some/path/here"};
        String publicDirectory = commandLineArgumentParser.extractPublicDirectory(commandLineArguments);

        assertThat(publicDirectory, is("/some/path/here"));
    }

    @Test
    public void publicDirectoryDefaultsWhenNotSpecified() {
        CommandLineArgumentParser commandLineArgumentParser = new CommandLineArgumentParser();
        String[] commandLineArguments = new String[]{};
        String publicDirectory = commandLineArgumentParser.extractPublicDirectory(commandLineArguments);

        assertThat(publicDirectory, is("/Users/Georgina/Documents/Projects/cob-server/cob_spec/public"));
    }
}
