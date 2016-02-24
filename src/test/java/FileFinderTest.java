import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FileFinderTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    private String absolutePath;

    @Before
    public void setup() throws IOException {
        temporaryFolder.create();
        temporaryFolder.newFolder("publicRoot");
        absolutePath = temporaryFolder.getRoot().getAbsolutePath();
    }

    @Test
    public void looksupExistingResource() throws IOException {
        File resource = setupResourceWithContent();
        ResourceFinder resourceFinder = new FileFinder(absolutePath);
        byte[] resourceContent = resourceFinder.getContentOf("/" + resource.getName());

        assertThat(resourceContent, is("My=Data".getBytes()));
    }

    @Test
    public void looksupNonExistingResource() {
        ResourceFinder resourceFinder = new FileFinder(absolutePath);
        byte[] resourceContent = resourceFinder.getContentOf("non-existing-resource");

        assertThat(resourceContent, CoreMatchers.nullValue());
    }

    private File setupResourceWithContent() throws IOException {

        File resource = temporaryFolder.newFile("resource");
        Writer fileWriter = new FileWriter(resource.getPath());
        fileWriter.write("My=Data");
        fileWriter.flush();
        fileWriter.close();

        return resource;
    }
}
