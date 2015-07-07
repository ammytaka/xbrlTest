package tokyo.schole.controller;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class XbrlControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/xbrl");
        XbrlController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is("/xbrl.jsp"));
    }
}
