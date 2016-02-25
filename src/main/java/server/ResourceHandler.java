package server;

public interface ResourceHandler {
    void write(String filename, String content);
    boolean delete(String filename);
    byte[] read(String filename);
    String[] listDirectoryContent();
}
