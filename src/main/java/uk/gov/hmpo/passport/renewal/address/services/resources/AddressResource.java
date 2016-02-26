package uk.gov.hmpo.passport.renewal.address.services.resources;

import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.*;
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
@Api(value = "/addresses/{moniker}", description = "Return a specific address by moniker", produces = MediaType.APPLICATION_JSON)
public class AddressResource {

    /**
     * The logger
     */
    private static final Logger logger = LoggerFactory
            .getLogger(AddressResource.class.getName());

    protected final QASSearch qasSearch;

    public AddressResource(QASSearch qasSearch) {
        this.qasSearch = qasSearch;
    }

    @GET
    @Timed
    @ApiOperation(value = "Get an Address by moniker", response = Address.class)
    @ApiResponses(@ApiResponse(code = 404, message = "Not Found"))
    public Address getAddress(@ApiParam(value = "Moniker of the address", required = true) @PathParam("moniker") String id) {
        logger.debug("Get Method Detected at /addresses/{moniker}.json");

        try {

            Address address = qasSearch.getAddress(id);
            return address;
        } catch (javax.xml.ws.soap.SOAPFaultException e) {
            throw new WebApplicationException(e, Response.Status.NOT_FOUND);
        }
    }
}
