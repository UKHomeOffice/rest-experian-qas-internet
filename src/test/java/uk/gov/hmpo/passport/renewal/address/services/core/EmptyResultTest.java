package uk.gov.hmpo.passport.renewal.address.services.core;

import junit.framework.TestCase;
import uk.gov.hmpo.passport.renewal.address.services.resources.stub.EmptyAddressResource;
import uk.gov.hmpo.passport.renewal.address.services.resources.stub.EmptySearchResultsResource;

import java.util.List;

/**
 * Created by jcollins on 13/04/15.
 */
public class EmptyResultTest extends TestCase {

    public void testEmptyController() {
        EmptyAddressResource emptyAddressResource = new EmptyAddressResource();
        Address result = emptyAddressResource.getAddress("1");
        assertNotNull(result);
    }

    public void testEmptySearchController() {
        EmptySearchResultsResource emptySearchResultsResource = new EmptySearchResultsResource();
        List<Address> result = emptySearchResultsResource.getAddresses("1", "AB");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.size() == 10);
    }
}
