import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileResourceWriter implements ResourceWriter {
    private String absolutePath;

    public FileResourceWriter(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    @Override
    public void write(String fileName, String content) {
        File resource = new File(absolutePath + fileName);
        try {
            System.out.println("Going to write " + content + " to  " + absolutePath + fileName);
            FileWriter fileWriter = new FileWriter(resource);
            fileWriter.write(content);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            //TODO custom exception to be thrown here
        }
    }

    @Override
    public boolean delete(String fileName) {
        File resource = new File(absolutePath + fileName);
        return resource.delete();
    }
}
