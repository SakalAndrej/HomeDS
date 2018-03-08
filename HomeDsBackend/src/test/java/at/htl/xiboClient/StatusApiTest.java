package at.htl.xiboClient;

import at.htl.exceptions.NoConnectionException;
import at.htl.utils.AuthentificationHandler;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class StatusApiTest {

    StatusApi statusApi = new StatusApi();

    @Before
    public void setUp() throws Exception {
        AuthentificationHandler.Authenticate();
    }

    @Test
    public void getIsOnline() {
        try {
            assertThat(statusApi.getIsOnline(),is(true));
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
    }
}