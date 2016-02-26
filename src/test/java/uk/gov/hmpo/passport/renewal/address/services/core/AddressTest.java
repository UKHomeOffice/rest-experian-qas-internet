package uk.gov.hmpo.passport.renewal.address.services.core;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class AddressTest extends TestCase {

    private Address testInstance;

    public void setUp() throws Exception {
        super.setUp();
        testInstance = new Address();
    }

    public void testGetUprn() throws Exception {
        testInstance.setUprn("uprn");
        assertEquals(testInstance.getUprn(), "uprn");
    }

    public void testGetLines() throws Exception {
        List<String> expectedLines = new ArrayList<String>();
        expectedLines.add("testing");
        expectedLines.add("testing 2");
        expectedLines.add("testing 1");

        testInstance.setLines(expectedLines);

        assertEquals(testInstance.getLines(), expectedLines);
    }

    public void testGetPostcode() throws Exception {
        testInstance.setPostcode("TE5 7PO");
        assertEquals(testInstance.getPostcode(), "TE5 7PO");
    }

    public void testGetMoniker() throws Exception {
        testInstance.setMoniker("Monkier");
        assertEquals(testInstance.getMoniker(), "Monkier");
    }

    public void testGetPartial() throws Exception {
        testInstance.setPartial("Partial");
        assertEquals(testInstance.getPartial(), "Partial");
    }

    public void testGetTown() throws Exception {
        testInstance.setTown("Townton");
        assertEquals(testInstance.getTown(), "Townton");
    }

    public void testGetEasting() throws Exception {
        testInstance.setEasting("westing");
        assertEquals(testInstance.getEasting(), "westing");
    }

    public void testGetNorthing() throws Exception {
        testInstance.setNorthing("Southing");
        assertEquals(testInstance.getNorthing(), "Southing");
    }

    public void testGetCountry() throws Exception {
        testInstance.setCountry("GBR");
        assertEquals(testInstance.getCountry(), "GBR");
    }

    public void testGetDependentLocality() throws Exception {
        testInstance.setDependentLocality("GB");
        assertEquals(testInstance.getDependentLocality(), "GB");
    }

    public void testGetDependentThroughfare() throws Exception {
        testInstance.setDependentThroughfare("Test");
        assertEquals(testInstance.getDependentThroughfare(), "Test");
    }


    public void testGetAdministrativeArea() throws Exception {
        testInstance.setAdministrativeArea("testing");
        assertEquals(testInstance.getAdministrativeArea(), "testing");
    }

    public void testGetLocalAuthorityUpdateDate() throws Exception {
        testInstance.setLocalAuthorityUpdateDate("testing");
        assertEquals(testInstance.getLocalAuthorityUpdateDate(), "testing");
    }


    public void testGetRoyalMailUpdateDate() throws Exception {
        testInstance.setRoyalMailUpdateDate("rmudate");

        assertEquals(testInstance.getRoyalMailUpdateDate(), "rmudate");
    }


    public void testToString() throws Exception {
        List<String> expectedLines = new ArrayList<String>();
        expectedLines.add("Address Line 1");
        expectedLines.add("Address Line 2");
        expectedLines.add("Address Line 3");

        testInstance.setTown("Testton");
        testInstance.setLines(expectedLines);
        testInstance.setUprn("testing-1112");
        testInstance.setPostcode("TE5 7PO");

        assertEquals(testInstance.toString(), "[uprn=testing-1112]Address Line 1, Address Line 2, Address Line 3, Testton, TE5 7PO");
    }
}