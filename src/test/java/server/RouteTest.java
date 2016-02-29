package server;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class RouteTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void getsSupportedRoute() {
        assertThat(Route.getRouteFor("/"), is(Route.HOME));
    }

    @Test
    public void isSupportedRoute() {
        assertThat(Route.isSupported("/"), is(true));
    }

    @Test
    public void isNotASupportedRoute() {
       assertThat(Route.isSupported("Unsupported!"), is(false));
    }

    @Test
    public void throwsExceptionIfRouteIsNotSupported() {
        expectedException.expectMessage("Unsupported route!");
        expectedException.expect(instanceOf(IllegalArgumentException.class));

        Route.getRouteFor("unsupported");
    }
}
