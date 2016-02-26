package uk.gov.hmpo.passport.renewal.address.services.resources;

import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmpo.passport.renewal.address.services.core.AddressLookupStatus;
import uk.gov.hmpo.passport.renewal.address.services.util.QASSearch;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/status.json")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(value = "/status", description = "Can an address moniker be queried", produces = MediaType.APPLICATION_JSON)
public class AddressLookupStatusResource {

    /**
     * The logger
     */
    private static final Logger logger = LoggerFactory
            .getLogger(AddressLookupStatusResource.class.getName());

    protected final QASSearch qasSearch;

    public AddressLookupStatusResource(QASSearch qasSearch) {
        this.qasSearch = qasSearch;
    }

    @GET
    @Timed
    @ApiOperation(value = "Can an address moniker be queried", response = AddressLookupStatus.class)
    public AddressLookupStatus getAddress(@ApiParam(value = "Moniker of the address", required = true) @PathParam("id") String id) {
        logger.debug("Get Method Detected at /status.json");

        boolean available = qasSearch.canSearch();
        AddressLookupStatus status = new AddressLookupStatus();
        status.setAvailable(available);

        return status;
    }
}
