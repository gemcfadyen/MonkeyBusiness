package server.actions;

import java.util.List;

public class FormatListAsCommaDelimitedContent<T> {

   public String commaDelimitAllowParameters(List<T> values) {
        String commaSeparatedValues = "";
        T firstValue;
        if (values.size() > 0) {
            firstValue = values.get(0);
            for (int i = 1; i < values.size(); i++) {
                commaSeparatedValues += "," + values.get(i);
            }
            return firstValue + commaSeparatedValues;
        }
        return commaSeparatedValues;
    }
}
