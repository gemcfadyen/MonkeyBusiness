import java.io.InputStream;

interface HttpRequestParser {

    HttpRequest parse(InputStream inputStream);

}
