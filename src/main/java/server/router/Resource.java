package server.router;

import java.util.Arrays;
import java.util.List;

public enum Resource {

    HOME("/"),
    FORM("/form"),
    METHOD_OPTIONS("/method_options"),
    REDIRECT("/redirect"),
    IMAGE_JPEG("/image.jpeg"),
    IMAGE_PNG("/image.png"),
    IMAGE_GIF("/image.gif"),
    FILE("/file1"),
    TEXT_FILE("/text-file.txt"),
    PARAMETERS("/parameters"),
    LOGS("/logs"),
    LOG("/log"),
    THESE("/these"),
    REQUESTS("/requests"),
    PARTIAL_CONTENT("/partial_content.txt"),
    PATCH_CONTENT("/patch-content.txt");

    private final String path;

    Resource(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public static boolean isSupported(String route) {
        List<Resource> allSupportedResources = Arrays.asList(values());
        return allSupportedResources.stream().anyMatch(x -> x.getPath().equals(route));
    }
}
