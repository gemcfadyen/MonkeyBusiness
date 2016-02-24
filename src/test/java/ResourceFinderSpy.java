public class ResourceFinderSpy implements ResourceFinder {
    private boolean hasLookedUpResource;

    @Override
    public byte[] getContentOf(String resourcePath) {
        hasLookedUpResource = true;
        return "My=Data".getBytes();
    }

    public boolean hasLookedupResource() {
        return hasLookedUpResource;
    }
}
