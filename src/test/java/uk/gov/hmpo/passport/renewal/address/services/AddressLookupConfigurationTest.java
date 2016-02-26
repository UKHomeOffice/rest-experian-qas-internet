package uk.gov.hmpo.passport.renewal.address.services;

import junit.framework.TestCase;

public class AddressLookupConfigurationTest extends TestCase {

    AddressLookupConfiguration testInstance;

    public void setUp() throws Exception {
        super.setUp();

        testInstance = new AddressLookupConfiguration();
    }

    public void testGetIgnoreLabels() throws Exception {
        testInstance.setIgnoreLabels("test");

        assertEquals("test", testInstance.getIgnoreLabels());
    }

    public void testGetEastingLabel() throws Exception {
        testInstance.setEastingLabel("test");

        assertEquals("test", testInstance.getEastingLabel());
    }

    public void testGetNorthingLabel() throws Exception {
        testInstance.setNorthingLabel("test");

        assertEquals("test", testInstance.getNorthingLabel());
    }

    public void testGetUsername() throws Exception {
        testInstance.setUsername("test");

        assertEquals("test", testInstance.getUsername());
    }

    public void testGetPassword() throws Exception {
        testInstance.setPassword("test");

        assertEquals("test", testInstance.getPassword());
    }

    public void testGetCountry() throws Exception {
        testInstance.setCountry("test");

        assertEquals("test", testInstance.getCountry());
    }

    public void testGetLayout() throws Exception {
        testInstance.setLayout("test");

        assertEquals("test", testInstance.getLayout());
    }

    public void testGetUprnLabel() throws Exception {
        testInstance.setUprnLabel("test");

        assertEquals("test", testInstance.getUprnLabel());
    }

    public void testGetPostcodeLabel() throws Exception {
        testInstance.setPostcodeLabel("test");

        assertEquals("test", testInstance.getPostcodeLabel());
    }

    public void testGetTownLabel() throws Exception {
        testInstance.setTownLabel("test");

        assertEquals("test", testInstance.getTownLabel());
    }

    public void testGetCountryLabel() throws Exception {
        testInstance.setCountryLabel("test");

        assertEquals("test", testInstance.getCountryLabel());
    }

    public void testGetDependentLocalityLabel() throws Exception {
        testInstance.setDependentLocalityLabel("test");

        assertEquals("test", testInstance.getDependentLocalityLabel());
    }

    public void testGetDependentThroughfareLabel() throws Exception {
        testInstance.setDependentThroughfareLabel("test");

        assertEquals("test", testInstance.getDependentThroughfareLabel());
    }

    public void testGetAdministrativeAreaLabel() throws Exception {
        testInstance.setAdministrativeAreaLabel("test");

        assertEquals("test", testInstance.getAdministrativeAreaLabel());
    }

    public void testGetLocalAuthorityUpdateDateLabel() throws Exception {
        testInstance.setLocalAuthorityUpdateDateLabel("test");

        assertEquals("test", testInstance.getLocalAuthorityUpdateDateLabel());
    }

    public void testGetRoyalMailUpdateDateLabel() throws Exception {
        testInstance.setRoyalMailUpdateDateLabel("test");

        assertEquals("test",testInstance.getRoyalMailUpdateDateLabel());
    }
}