package uk.gov.hmpo.passport.renewal.address.services.resources;

import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmpo.passport.renewal.address.services.core.Address;
import uk.gov.hmpo.passport.renewal.address.services.util.QASSearch;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/addresses.json")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(value = "/addresses", description = "Search for addresses by house number and postcode", produces = MediaType.APPLICATION_JSON)
public class SearchResultsResource {

    /**
     * The logger
     */
    private static final Logger logger = LoggerFactory
            .getLogger(SearchResultsResource.class);

    protected final QASSearch qasSearch;

    public SearchResultsResource(QASSearch qasSearch) {
        this.qasSearch = qasSearch;
    }

    @GET
    @Timed
    @ApiOperation(value = "Search for addresses with a postcode and house number", response = Address.class, responseContainer = "List")
    public List<Address> getAddresses(
            @ApiParam(value = "House number", required = true)
            @QueryParam("houseNumber") String houseNumber,
            @ApiParam(value = "Postcode", required = true)
            @QueryParam("postcode") String postcode) {
        if (logger.isDebugEnabled()) {
            logger.debug("Get Method Detected at /addresses");
            logger.debug("houseNumber=" + houseNumber + ", postcode="
                    + postcode);
        }

        return qasSearch.getAddresses(houseNumber, postcode);
    }
}
