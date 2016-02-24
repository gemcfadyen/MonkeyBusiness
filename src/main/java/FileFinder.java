import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class FileFinder implements ResourceFinder {
    private String rootPath;

    public FileFinder(String rootPath) {
        this.rootPath = rootPath;
    }

    public byte[] getContentOf(String resourcePath) {
        try {
            System.out.println("Looking up resource at location: " + rootPath + resourcePath);
            return Files.readAllBytes(Paths.get(filename(resourcePath)));
        } catch (IOException e) {
            System.out.println("FILE IS NOT Found!!!");
            return noResourceContentAvailable();
        }
    }

    private String filename(String resourcePath) {
        return rootPath + resourcePath;
    }

    private byte[] noResourceContentAvailable() {
        return null;
    }
}
