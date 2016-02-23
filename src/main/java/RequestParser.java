import java.io.InputStream;

interface RequestParser {

    HttpRequest parse(InputStream inputStream);

}
