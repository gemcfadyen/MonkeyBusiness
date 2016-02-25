import org.hamcrest.Matchers;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class RouteKeyTest {

    @Test
    public void routingKeysAreEqual() {
        RouteKey routingKey1 = new RouteKey("/", "GET");
        RouteKey routingKey2 = new RouteKey("/", "GET");

        assertThat(routingKey1, is(equalTo(routingKey2)));
        assertThat(routingKey1.hashCode(), is(equalTo(routingKey2.hashCode())));
    }

    @Test
    public void routeKeysWithDifferentMethodsNotEqual() {
        RouteKey routingKey1 = new RouteKey("/", "GET");
        RouteKey routingKey2 = new RouteKey("/", "POST");

        assertThat(routingKey1, not(equalTo(routingKey2)));
        assertThat(routingKey1.hashCode(), not(equalTo(routingKey2.hashCode())));
    }

    @Test
    public void routeKeysWithDifferentRoutesAreNotEqual() {
        RouteKey routingKey1 = new RouteKey("/another", "GET");
        RouteKey routingKey2 = new RouteKey("/", "GET");

        assertThat(routingKey1, not(equalTo(routingKey2)));
        assertThat(routingKey1.hashCode(), not(equalTo(routingKey2.hashCode())));
    }
}
