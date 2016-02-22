import java.util.Arrays;

public class HttpServerRunner {


    public static void main(String... args) {

    }

    public int extractPort(String... args) {
        int defaultPort = 5000;
        int indexOfPortFlag = Arrays.asList(args).indexOf("-p");
        if (indexOfPortFlag != -1) {
            String portAsString = Arrays.asList(args).get(indexOfPortFlag + 1);

            try {
                return Integer.valueOf(portAsString);
            } catch (NumberFormatException e) {
                return defaultPort;
            }

        } else {
            return defaultPort;
        }
    }

    public String extractPublicDirectory(String[] args) {
        String defaultPublicDirectory = "/Users/Georgina/Documents/Projects/cob-server/cob_spec/public";
        int indexOfPublicDirectoryFlag = Arrays.asList(args).indexOf("-d");
        if (indexOfPublicDirectoryFlag != -1) {
            String publicDirectory = Arrays.asList(args).get(indexOfPublicDirectoryFlag + 1);
            return publicDirectory;

        } else {
            return defaultPublicDirectory;
        }
    }
}
