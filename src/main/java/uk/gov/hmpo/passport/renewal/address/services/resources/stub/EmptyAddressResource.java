package uk.gov.hmpo.passport.renewal.address.services.resources.stub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmpo.passport.renewal.address.services.core.Address;
import uk.gov.hmpo.passport.renewal.address.services.util.QASSearch;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/addresses/{moniker}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmptyAddressResource {

    /**
     * The logger
     */
    private static final Logger logger = LoggerFactory
            .getLogger(EmptyAddressResource.class.getName());

    public EmptyAddressResource() {
    }

    @GET
    public Address getAddress(@PathParam("moniker") String id) {
        logger.debug("Get Method Detected at /addresses/{moniker}.json");

        try {

            return populate(new Integer(id));

        } catch (javax.xml.ws.soap.SOAPFaultException e) {
            throw new WebApplicationException(e, Response.Status.NOT_FOUND);
        }
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
