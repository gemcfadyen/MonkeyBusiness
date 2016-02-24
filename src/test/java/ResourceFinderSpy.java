public class ResourceFinderSpy implements ResourceFinder {
    private boolean hasLookedUpResource;

    @Override
    public String getContentOf(String resourcePath) {
        hasLookedUpResource = true;
        return "My=Data";
    }

    public boolean hasLookedupResource() {
        return hasLookedUpResource;
    }
}
