package server;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class FileResourceHandler implements ResourceHandler {
    private String absolutePath;

    public FileResourceHandler(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    @Override
    public void write(String filename, String content) {
        try {
            writeContentToFile(filename, content, false);
        } catch (IOException e) {
            throw new ResourceWriteException("Exception in writing to file " + filename, e);
        }
    }

    @Override
    public void append(String filename, String content) {
        try {
            writeContentToFile(filename, content, true);
        } catch (IOException e) {
            throw new ResourceWriteException("Exception in appending to file " + filename, e);
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
            return Files.readAllBytes(Paths.get(fullPath(filename)));
        } catch (IOException e) {
            return noResourceContentAvailable();
        }
    }

   // @Override
    public byte[] readByteRange(String filename, int startingByte, int finishingByte) {

        byte[] readResource = read(filename);

        byte[] portionToReturn = new byte[finishingByte - startingByte];
       return  Arrays.copyOfRange(readResource, startingByte, finishingByte);


//        try {
//            FileReader fileReader = new FileReader(fullPath(filename));
//            char[] readRange = new char[finishingByte - startingByte];
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//            bufferedReader.read(readRange, startingByte, finishingByte);
//            System.out.println("READ TEH RANGE : " + String.valueOf(readRange));
//            return String.valueOf(readRange).getBytes();
//
//        } catch (IOException e) { //TODO test for custom exception thrown here
//            e.printStackTrace();
//        }
//        return new byte[0]; //TODO is this sensible?
    }

    @Override
    public String[] directoryContent() {
        File rootDirectory = new File(absolutePath);
        return rootDirectory.list();
    }

    private byte[] noResourceContentAvailable() {
        return new byte[0];
    }

    private String fullPath(String fileName) {
        return absolutePath + fileName;
    }

    protected void writeContentToFile(String filename, String content, boolean shouldAppend) throws IOException {
        File resource = new File(fullPath(filename));
        FileWriter fileWriter = new FileWriter(resource, shouldAppend);
        fileWriter.write(content);
        fileWriter.flush();
        fileWriter.close();
    }
}
