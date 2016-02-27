package server;

public class ResourceHandlerSpy implements ResourceHandler {
    private boolean hasWrittenFile;
    private boolean hasDeletedResource;
    private boolean hasReadResource;
    private boolean hasGotDirectoryContent;
    private boolean hasAppended;

    private String fileName;
    private String contentAppendedToResource;
    private boolean hasReadPortionOfResource;

    @Override
    public void write(String filename, String content) {
        this.fileName = filename;
        hasWrittenFile = true;
    }

    @Override
    public void append(String filename, String content) {
        this.fileName = filename;
        this.contentAppendedToResource = content;
        hasAppended = true;
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
    public byte[] readByteRange(String filename, int startingByte, int finishingByte) {
        hasReadPortionOfResource = true;
        return "My".getBytes();
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

    public String getNameOfResourceThatWasChanged() {
       return fileName;
    }

    public boolean hasAppendedToResource() {
       return hasAppended;
    }

    public String getContentAppendedToResource() {
        return contentAppendedToResource;
    }

    public boolean hasReadPortionOfResource() {
       return hasReadPortionOfResource;
    }
}
