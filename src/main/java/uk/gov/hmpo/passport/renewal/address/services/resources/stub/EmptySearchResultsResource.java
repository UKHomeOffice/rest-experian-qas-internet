package uk.gov.hmpo.passport.renewal.address.services.resources.stub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmpo.passport.renewal.address.services.core.Address;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Path("/addresses.json")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmptySearchResultsResource {

    /**
     * The logger
     */
    private static final Logger logger = LoggerFactory
            .getLogger(EmptySearchResultsResource.class);

    @GET
    public List<Address> getAddresses(
            @QueryParam("houseNumber") String houseNumber,
            @QueryParam("postcode") String postcode) {
        if (logger.isDebugEnabled()) {
            logger.debug("Get Method Detected at /addresses");
            logger.debug("houseNumber=" + houseNumber + ", postcode="
                    + postcode);
        }

        List<Address> addresses = new ArrayList<Address>();

        IntStream.rangeClosed(1, 10).forEach(i -> addresses.add(populate(i)));

        return addresses;
    }

    // Pre-populate object with random data
    public Address populate(int random) {

        Address address = new Address();

        String text = "TEST: " + random;

        address.setAdministrativeArea(text);
        address.setCountry(text);
        address.setDependentLocality(text);
        address.setDependentThroughfare(text);
        address.setEasting(text);
        address.setLocalAuthorityUpdateDate(text);
        address.setMoniker(text);
        address.setNorthing(text);
        address.setPartial(text);
        address.setPostcode(text);
        address.setTown(text);
        address.setUprn(text);

        return address;
    }
}
