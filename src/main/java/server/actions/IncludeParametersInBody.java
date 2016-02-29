package server.actions;

import server.Action;
import server.messages.DelimitedFormatter;
import server.messages.StatusCode;
import server.messages.HttpRequest;
import server.messages.HttpResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static server.messages.Delimiter.COMMA;
import static server.messages.HttpResponseBuilder.anHttpResponseBuilder;

public class IncludeParametersInBody implements Action {
    private DelimitedFormatter<String> delimitedFormatter = new DelimitedFormatter<>();

    @Override
    public HttpResponse process(HttpRequest request) {
        String commaSeparatedListOfParameters =
                delimitedFormatter.delimitedValues(getParametersFrom(request), COMMA);

        return anHttpResponseBuilder()
                .withStatusCode(StatusCode.OK)
                .withBody(commaSeparatedListOfParameters.getBytes())
                .build();
    }

    private List<String> getParametersFrom(HttpRequest request) {
        List<String> parameters = new ArrayList<>();
        Map<String, String> requestParameters = request.params();
        parameters.addAll(requestParameters.entrySet().stream()
                .map(entry -> entry.getKey() + " = " + entry.getValue())
                .collect(Collectors.toList()));
        return parameters;
    }
}
