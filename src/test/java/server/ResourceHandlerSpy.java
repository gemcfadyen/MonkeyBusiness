package server;

public class ResourceHandlerSpy implements ResourceHandler {
    private boolean hasWrittenFile;
    private boolean hasDeletedResource;
    private boolean hasReadResource;
    private boolean hasGotDirectoryContent;

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
        return "My=Data".getBytes();
    }

    @Override
    public String[] directoryContent() {
        hasGotDirectoryContent = true;
        return new String[]{"file1", "file2"};
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

    public boolean hasGotDirectoryContent() {
        return hasGotDirectoryContent;
    }
}
