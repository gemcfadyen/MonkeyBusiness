import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class FileFinder implements ResourceFinder {
    private String rootPath;

    public FileFinder(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getContentOf(String resourcePath) {
        try {
            System.out.println("Looking up resource at location: " + rootPath + resourcePath);
            String content = readContentsOfFile(filename(resourcePath));
            System.out.println("contents of file found " + content);
            return content;
        } catch (IOException e) {
            System.out.println("FILE IS NOT Found!!!");
            return noResourceContentAvailable();
        }
    }

    private String filename(String resourcePath) {
        return rootPath + resourcePath;
    }

    private String readContentsOfFile(String filename) throws IOException {
        BufferedReader resourceReader = new BufferedReader(new FileReader(filename));
        StringBuilder content = new StringBuilder("");

        String line = readLine(resourceReader);

        while (hasContent(line)) {
            content.append(line);
            line = readLine(resourceReader);
        }
        return content.toString();
    }

    private String readLine(BufferedReader resourceReader) throws IOException {
        return resourceReader.readLine();
    }

    private boolean hasContent(String line) {
        return line != null;
    }

    private String noResourceContentAvailable() {
        return null;
    }
}
