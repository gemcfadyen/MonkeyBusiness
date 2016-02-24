public class ResourceWriterSpy implements ResourceWriter {
    private boolean hasWrittenFile;

    @Override
    public void write(String fileName, String content) {
        hasWrittenFile = true;
    }

    public boolean hasCreatedResource() {
        return hasWrittenFile;
    }
}
