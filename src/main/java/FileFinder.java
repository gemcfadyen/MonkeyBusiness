import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

class FileFinder implements ResourceFinder {
    private String rootPath;

    public FileFinder(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getContentOf(String resourcePath) {
        try {
            System.out.println("Looking up resource at location: " + rootPath + resourcePath);
            Reader fileReader = new FileReader(rootPath + resourcePath);
            BufferedReader resourceReader = new BufferedReader(fileReader);//TODO inject in?

            StringBuffer content = new StringBuffer("");
            String line = resourceReader.readLine();

            while (line != null) {
                content.append(line);
                line = resourceReader.readLine();
            }
            System.out.println("Found file " + content.toString());
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("FILE IS NOT Found!!!");
            return null;
        }

    }
}
