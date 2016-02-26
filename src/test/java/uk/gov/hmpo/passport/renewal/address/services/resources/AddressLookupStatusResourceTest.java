package uk.gov.hmpo.passport.renewal.address.services.resources;

import junit.framework.TestCase;
import uk.gov.hmpo.passport.renewal.address.services.core.AddressLookupStatus;
import uk.gov.hmpo.passport.renewal.address.services.util.QASSearch;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddressLookupStatusResourceTest extends TestCase {

    AddressLookupStatusResource testInstance;
    QASSearch mockApi;


    public void setUp() throws Exception {
        super.setUp();

        mockApi = mock(QASSearch.class);

        testInstance = new AddressLookupStatusResource(mockApi);
    }

    public void testGetAddress() throws Exception {
        when(mockApi.canSearch()).thenReturn(true);

        assertEquals(testInstance.getAddress("anything").isAvailable(), true);
    }
}