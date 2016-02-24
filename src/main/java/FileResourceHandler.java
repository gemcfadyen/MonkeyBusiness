import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileResourceHandler implements ResourceHandler {
    private String absolutePath;

    public FileResourceHandler(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    @Override
    public void write(String filename, String content) {
        try {
            System.out.println("Going to write " + content + " to  " + absolutePath + filename);
            writeContentToFile(filename, content);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResourceWriteException("Exception in writing to file " + filename, e);
        }
    }

    @Override
    public boolean delete(String fileName) {
        File resource = new File(filename(fileName));
        return resource.delete();
    }

    private String filename(String fileName) {
        return absolutePath + fileName;
    }

    //TODO should this be injected, but then what is the responsibilities of this class
    protected void writeContentToFile(String filename, String content) throws IOException {
        File resource = new File(filename(filename));
        FileWriter fileWriter = new FileWriter(resource);
        fileWriter.write(content);
        fileWriter.flush();
        fileWriter.close();
    }
}
