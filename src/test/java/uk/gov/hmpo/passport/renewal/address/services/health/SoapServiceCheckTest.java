package uk.gov.hmpo.passport.renewal.address.services.health;

import com.codahale.metrics.health.HealthCheck;
import junit.framework.TestCase;
import uk.gov.hmpo.passport.renewal.address.services.util.QASSearch;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SoapServiceCheckTest extends TestCase {

    SoapServiceCheck testInstance;
    QASSearch mockApi;


    public void setUp() throws Exception {
        super.setUp();

        mockApi = mock(QASSearch.class);

        testInstance = new SoapServiceCheck(mockApi, "DEV");

    }

    public void testCheck() throws Exception {
        when(mockApi.checkHealth()).thenReturn(null);
        assertEquals(testInstance.check(), HealthCheck.Result.healthy());
    }
}