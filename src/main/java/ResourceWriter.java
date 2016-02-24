public interface ResourceWriter {
    void write(String fileName, String content);
    boolean delete(String filename);
}
