package uk.gov.hmpo.passport.renewal.address.services.resources;

import junit.framework.TestCase;
import uk.gov.hmpo.passport.renewal.address.services.util.QASSearch;
import uk.gov.hmpo.passport.renewal.address.services.core.Address;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.when;

public class AddressResourceTest extends TestCase {

    AddressResource testInstance;
    QASSearch mockApi;

    public void setUp() throws Exception {
        super.setUp();

        mockApi = mock(QASSearch.class);

        testInstance = new AddressResource(mockApi);

    }

    public void testGetAddress() throws Exception {
        Address expectedAddress = new Address();
        expectedAddress.setUprn("testing");

        when(mockApi.getAddress("113422")).thenReturn(expectedAddress);

        assertEquals(testInstance.getAddress("113422"), expectedAddress);
    }
}