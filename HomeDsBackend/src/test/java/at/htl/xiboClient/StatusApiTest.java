package at.htl.xiboClient;

import at.htl.exceptions.NoConnectionException;
import at.htl.utils.AuthentificationHandler;
import at.htl.utils.RequestHelper;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class StatusApiTest {

    private final StatusApi statusApi = new StatusApi();

    @Before
    public void setUp() {
        AuthentificationHandler.Authenticate();
    }

    @Test
    public void getIsOnline() {
        try {
            assertThat("It could not be established a connection maybe connection string incorrect" + new RequestHelper().BASE_URL ,statusApi.getIsOnline(),is(true));
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
    }
}