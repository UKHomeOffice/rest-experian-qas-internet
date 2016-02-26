package uk.gov.hmpo.passport.renewal.address.services.util;

import com.qas.ondemand_2011_03.QASOnDemandIntermediary;
import junit.framework.TestCase;
import uk.gov.hmpo.passport.renewal.address.services.core.Address;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class QASearchIntegrationTest extends TestCase {

    QASSearchImpl addressSearch;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        URLSetup.setup();

        String qasUsername = System.getenv("WCRS_ADDRESS_USER");
        String qasPassword = System.getenv("WCRS_ADDRESS_PASSWORD");

        addressSearch = new QASSearchImpl(
                qasUsername,
                qasPassword,
                "APR",
                "EACustom",
                (new QASOnDemandIntermediary())
        );
        addressSearch.setDependentLocalityLabel("RM Dependent Locality");
        addressSearch.setNorthingLabel("BLPU Northing");
        addressSearch.setEastingLabel("BLPU Easting");

        addressSearch.setTownLabel("RM Post Town");
        List<String> ignore = new LinkedList<String>();
        ignore.add("RM Double Dependent Locality");
        addressSearch.setIgnoreLabels(ignore);
    }

    public void testCanSearch() {
        assertEquals(true, addressSearch.canSearch());
    }

    public void testGetAddresses() {
        getAddressTestForPostcode("TF3 4NT");
//		getAddressTestForPostcode("EN2 7AW");
    }

    public void getAddressTestForPostcode(String postcode) {
        List<Address> addresses = addressSearch.getAddresses(null, postcode);
        Set<String> monikers = new HashSet<String>();
        for (Address address : addresses) {
            String moniker = address.getMoniker();
            if (monikers.contains(moniker)) {
                fail("Multiple addresses for moniker!!: " + moniker);
            }
            monikers.add(moniker);
            assertEquals(postcode, address.getPostcode());
            Address fullAddress = addressSearch
                    .getAddress(address.getMoniker());
            assertNotNull(fullAddress);
            assertEquals(postcode, fullAddress.getPostcode());
        }

    }

    public void testGetAddresses_noResults() {
        List<Address> addresses = addressSearch.getAddresses(null, "BS1");
        assertEquals(0, addresses.size());
    }

}
