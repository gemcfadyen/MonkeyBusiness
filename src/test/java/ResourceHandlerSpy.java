public class ResourceHandlerSpy implements ResourceHandler {
    private boolean hasWrittenFile;
    private boolean hasDeletedResource;

    @Override
    public void write(String fileName, String content) {
        hasWrittenFile = true;
    }

    @Override
    public boolean delete(String filename) {
        hasDeletedResource = true;
        return false;
    }

    public boolean hasWrittenToResource() {
        return hasWrittenFile;
    }

    public boolean hasDeletedResource() {
        return hasDeletedResource;
    }
}
