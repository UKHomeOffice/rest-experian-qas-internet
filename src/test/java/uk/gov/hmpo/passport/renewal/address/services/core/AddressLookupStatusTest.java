package uk.gov.hmpo.passport.renewal.address.services.core;

import junit.framework.TestCase;

public class AddressLookupStatusTest extends TestCase {
    AddressLookupStatus testInstance;

    public void setUp() throws Exception {
        super.setUp();
        testInstance = new AddressLookupStatus();
    }

    public void testIsAvailable() throws Exception {
        testInstance.setAvailable(true);
        assertEquals(testInstance.isAvailable(), true);
    }
}