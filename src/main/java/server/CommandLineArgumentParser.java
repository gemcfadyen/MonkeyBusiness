package server;

import java.util.Arrays;

public class CommandLineArgumentParser {
    private static final int DEFAULT_PORT = 5000;
    private static final String DEFAULT_PUBLIC_DIRECTORY = System.getProperty("user.home") + "/Documents/Projects/cob-server/cob_spec/public";

    public int extractPort(String... commandLineArgs) {
        int indexOfPortFlag = Arrays.asList(commandLineArgs).indexOf("-p");
        if (parameterKeyIsfound(indexOfPortFlag)) {
            String portAsString = extractParameterValue(commandLineArgs, indexOfPortFlag);

            try {
                return convertToInteger(portAsString);
            } catch (NumberFormatException e) {
                return DEFAULT_PORT;
            }

        } else {
            return DEFAULT_PORT;
        }
    }

    public String extractPublicDirectory(String[] commandLineArgs) {
        int indexOfPublicDirectoryFlag = Arrays.asList(commandLineArgs).indexOf("-d");
        if (parameterKeyIsfound(indexOfPublicDirectoryFlag)) {
            return extractParameterValue(commandLineArgs, indexOfPublicDirectoryFlag);
        } else {
            return DEFAULT_PUBLIC_DIRECTORY;
        }
    }

    private Integer convertToInteger(String portAsString) {
        return Integer.valueOf(portAsString);
    }

    private String extractParameterValue(String[] args, int indexOfPublicDirectoryFlag) {
        return Arrays.asList(args).get(indexOfPublicDirectoryFlag + 1);
    }

    private boolean parameterKeyIsfound(int indexOfPublicDirectoryFlag) {
        return indexOfPublicDirectoryFlag != -1;
    }
}
