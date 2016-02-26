package uk.gov.hmpo.passport.renewal.address.services.resources;

import junit.framework.TestCase;
import uk.gov.hmpo.passport.renewal.address.services.core.Address;
import uk.gov.hmpo.passport.renewal.address.services.util.QASSearch;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SearchResultsResourceTest extends TestCase {

    SearchResultsResource testInstance;
    QASSearch mockApi;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mockApi = mock(QASSearch.class);
        testInstance = new SearchResultsResource(mockApi);
    }

    public void testGetAddresses() throws Exception {
        List<Address> expectedAddresses = new ArrayList<Address>();
        Address expectedAddress1 = new Address();
        expectedAddress1.setUprn("1");

        Address expectedAddress2 = new Address();
        expectedAddress2.setUprn("2");

        expectedAddresses.add(expectedAddress1);
        expectedAddresses.add(expectedAddress2);

        when(mockApi.getAddresses("12", "SY21 1NX")).thenReturn(expectedAddresses);

        assertEquals(testInstance.getAddresses("12", "SY21 1NX"), expectedAddresses);

    }
}