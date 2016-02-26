package uk.gov.hmpo.passport.renewal.address.services.util;

import com.qas.ondemand_2011_03.*;
import junit.framework.TestCase;
import uk.gov.hmpo.passport.renewal.address.services.core.Address;

import javax.xml.ws.Holder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class QASSearchImplTest extends TestCase {

    private final String testUsername = "testUsername";
    private final String testPassword = "testPassword";
    private final String testCountry = "GBR";
    private final String testLayout = "testLayout";
    QASSearchImpl testInstance;
    QASOnDemandIntermediary mockIntermediary;
    QAPortType mockApi;

    public void setUp() throws Exception {
        super.setUp();

        mockApi = mock(QAPortType.class);
        mockIntermediary = mock(QASOnDemandIntermediary.class);
        when(mockIntermediary.getQAPortType()).thenReturn(mockApi);

        testInstance = new QASSearchImpl(
                testUsername,
                testPassword,
                testCountry,
                testLayout,
                mockIntermediary
        );
    }

    public void testCheckHealth() throws Exception {
        QASearchOk apiResponse = new QASearchOk();
        apiResponse.setIsOk(true);

        when(mockApi.doCanSearch(any(QACanSearch.class), any(QAQueryHeader.class), any(Holder.class))).thenReturn(apiResponse);

        assertEquals(testInstance.checkHealth(), null);
    }

    public void testCanSearch() throws Exception {
        QASearchOk apiResponse = new QASearchOk();
        apiResponse.setIsOk(true);

        when(mockApi.doCanSearch(any(QACanSearch.class), any(QAQueryHeader.class), any(Holder.class))).thenReturn(apiResponse);

        assertEquals(testInstance.canSearch(), true);
    }

    public void testGetAddress() throws Exception {
        com.qas.ondemand_2011_03.Address apiResponse = new com.qas.ondemand_2011_03.Address();
        QAAddressType apiAddress = new QAAddressType();
        apiResponse.setQAAddress(apiAddress);

        when(mockApi.doGetAddress(any(QAGetAddress.class), any(QAQueryHeader.class), any(Holder.class))).thenReturn(apiResponse);

        Address expectedAddress = new Address();
        expectedAddress.setMoniker("testAddress");
        expectedAddress.setLines(new LinkedList<String>());


        assertEquals(testInstance.getAddress("testAddress"), expectedAddress);
    }

    public void testGetAddresses() throws Exception {
        com.qas.ondemand_2011_03.QASearchResult apiResponse = new com.qas.ondemand_2011_03.QASearchResult();
        QAPicklistType picklist = new QAPicklistType();
        apiResponse.setQAPicklist(picklist);

        when(mockApi.doSearch(any(QASearch.class), any(QAQueryHeader.class), any(Holder.class))).thenReturn(apiResponse);

        Address expectedAddress = new Address();
        expectedAddress.setMoniker("testAddress");
        expectedAddress.setLines(new LinkedList<String>());

        List<Address> expectedAddresses = new ArrayList<Address>();
        List<Address> actualAddresses = testInstance.getAddresses("4", "SY23 1NX");

        assertEquals(actualAddresses, expectedAddresses);
    }
}