public class ResourceHandlerSpy implements ResourceHandler {
    private boolean hasWrittenFile;
    private boolean hasDeletedResource;
    private boolean hasReadResource;

    @Override
    public void write(String fileName, String content) {
        hasWrittenFile = true;
    }

    @Override
    public boolean delete(String filename) {
        hasDeletedResource = true;
        return false;
    }

    @Override
    public byte[] read(String filename) {
        hasReadResource = true;
        return new byte[0];
    }

    public boolean hasWrittenToResource() {
        return hasWrittenFile;
    }

    public boolean hasDeletedResource() {
        return hasDeletedResource;
    }

    public boolean hasReadResource() {
        return hasReadResource;
    }
}
