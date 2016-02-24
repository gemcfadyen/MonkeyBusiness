import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FileResourceWriterTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    private String absolutePath;
    private String resourceName = "/myData";

    @Before
    public void setup() throws IOException {
        temporaryFolder.create();
        temporaryFolder.newFolder("publicRoot");
        absolutePath = temporaryFolder.getRoot().getAbsolutePath();
    }

    @Test
    public void createsNewResource() {
        ResourceWriter resourceWriter = new FileResourceWriter(absolutePath);
        resourceWriter.write(resourceName, "My=Data");

        assertThat(getContentOfResource(), is("My=Data"));
    }

    @Test
    public void updatesAnExistingResource() {
        ResourceWriter resourceWriter = new FileResourceWriter(absolutePath);
        resourceWriter.write(resourceName, "originalData");
        resourceWriter.write(resourceName, "UpdatedData");

        assertThat(getContentOfResource(), is("UpdatedData"));
    }

    @Test
    public void triesToRemovesAResourceThatDoesntExist() {
        ResourceWriter resourceWriter = new FileResourceWriter(absolutePath);
        boolean deleted = resourceWriter.delete("Non-Existing-Resource");
        assertThat(deleted, is(false));
    }

    @Test
    public void removesExistingResource() {
        ResourceWriter resourceWriter = new FileResourceWriter(absolutePath);
        resourceWriter.write(resourceName, "My=Data");

        boolean isDeleted = resourceWriter.delete(resourceName);
        assertThat(isDeleted, is(true));
    }

    private String getContentOfResource() {
        ResourceFinder reader = new FileFinder(absolutePath);
        return reader.getContentOf(resourceName);
    }
}
