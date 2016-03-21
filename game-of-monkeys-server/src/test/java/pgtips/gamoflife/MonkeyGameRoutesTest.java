package pgtips.gamoflife;

import org.junit.Test;
import pgtips.gameoflife.MonkeyGameRoutes;
import server.Action;
import pgtips.gameoflife.MonkeyBusiness;
import server.router.HttpMethods;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MonkeyGameRoutesTest {
    @Test
    public void hasOneRoute() {
        MonkeyGameRoutes routes = new MonkeyGameRoutes(0);

        Map<HttpMethods, List<Action>> monkeyRoutes = routes.routes();
        List<Action> getRoutes = monkeyRoutes.get(HttpMethods.GET);

        assertThat(monkeyRoutes.size(), is(1));
        assertThat(getRoutes.size(), is(1));
        assertThat(getRoutes.get(0), instanceOf(MonkeyBusiness.class));
    }
}
