package server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        File resource = new File(fullPath(fileName));
        return resource.delete();
    }

    @Override
    public byte[] read(String filename) {
        try {
            System.out.println("Looking up resource at location: " + fullPath(filename));
            return Files.readAllBytes(Paths.get(fullPath(filename)));
        } catch (IOException e) {
            System.out.println("FILE IS NOT Found!!!");
            return noResourceContentAvailable();
        }
    }
    private byte[] noResourceContentAvailable() {
        return null;
    }
    private String fullPath(String fileName) {
        return absolutePath + fileName;
    }

    protected void writeContentToFile(String filename, String content) throws IOException {
        File resource = new File(fullPath(filename));
        FileWriter fileWriter = new FileWriter(resource);
        fileWriter.write(content);
        fileWriter.flush();
        fileWriter.close();
    }
}
