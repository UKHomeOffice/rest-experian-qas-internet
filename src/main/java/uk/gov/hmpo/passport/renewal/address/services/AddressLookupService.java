package uk.gov.hmpo.passport.renewal.address.services;

import com.qas.ondemand_2011_03.QASOnDemandIntermediary;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import uk.gov.hmpo.passport.renewal.address.services.core.Address;
import uk.gov.hmpo.passport.renewal.address.services.health.SoapServiceCheck;
import uk.gov.hmpo.passport.renewal.address.services.resources.AddressLookupStatusResource;
import uk.gov.hmpo.passport.renewal.address.services.resources.AddressResource;
import uk.gov.hmpo.passport.renewal.address.services.resources.SearchResultsResource;
import uk.gov.hmpo.passport.renewal.address.services.resources.stub.EmptyAddressResource;
import uk.gov.hmpo.passport.renewal.address.services.resources.stub.EmptySearchResultsResource;
import uk.gov.hmpo.passport.renewal.address.services.util.QASSearchImpl;
import uk.gov.hmpo.passport.renewal.address.services.util.URLSetup;

import java.util.LinkedList;
import java.util.List;

public class AddressLookupService extends Application<AddressLookupConfiguration> {


    public static void main(String[] args) throws Exception {
        new AddressLookupService().run(args);
    }

    @Override
    public String getName() {
        return "rest-experian-qas-internet";
    }

    @Override
    public void initialize(Bootstrap<AddressLookupConfiguration> bootstrap) {

        bootstrap.addBundle(new SwaggerBundle<AddressLookupConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(AddressLookupConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });

    }

    @Override
    public void run(AddressLookupConfiguration configuration,
                    Environment environment) throws Exception {

        String username = configuration.getUsername();
        String password = configuration.getPassword();
        String country = configuration.getCountry();
        String layout = configuration.getLayout();

        String postcodeLabel = configuration.getPostcodeLabel();
        String townLabel = configuration.getTownLabel();
        String uprnLabel = configuration.getUprnLabel();
        String eastingLabel = configuration.getEastingLabel();
        String northingLabel = configuration.getNorthingLabel();
        String dependentLocalityLabel = configuration
                .getDependentLocalityLabel();
        String dependentThroughfarLabel = configuration
                .getDependentThroughfareLabel();
        String administrativeAreaLabel = configuration
                .getAdministrativeAreaLabel();
        String localAuthorityUpdateDateLabel = configuration
                .getLocalAuthorityUpdateDateLabel();
        String royalMailUpdateDateLabel = configuration
                .getRoyalMailUpdateDateLabel();
        String ignoreLabels = configuration.getIgnoreLabels();

        URLSetup.setup();

        QASOnDemandIntermediary api = new QASOnDemandIntermediary();
        QASSearchImpl qasSearch = new QASSearchImpl(
                username,
                password,
                country,
                layout,
                api
        );

        if (townLabel != null) {
            qasSearch.setTownLabel(townLabel);
        }
        if (postcodeLabel != null) {
            qasSearch.setPostcodeLabel(postcodeLabel);
        }
        if (uprnLabel != null) {
            qasSearch.setUprnLabel(uprnLabel);
        }
        if (eastingLabel != null) {
            qasSearch.setEastingLabel(eastingLabel);
        }
        if (northingLabel != null) {
            qasSearch.setNorthingLabel(northingLabel);
        }
        if (dependentLocalityLabel != null) {
            qasSearch.setDependentLocalityLabel(dependentLocalityLabel);
        }
        if (dependentThroughfarLabel != null) {
            qasSearch.setDependentThroughfareLabel(dependentThroughfarLabel);
        }
        if (administrativeAreaLabel != null) {
            qasSearch.setAdministrativeAreaLabel(administrativeAreaLabel);
        }
        if (localAuthorityUpdateDateLabel != null) {
            qasSearch
                    .setLocalAuthorityUpdateDateLabel(localAuthorityUpdateDateLabel);
        }
        if (royalMailUpdateDateLabel != null) {
            qasSearch.setRoyalMailUpdateDateLabel(royalMailUpdateDateLabel);
        }

        if (ignoreLabels != null) {
            qasSearch.setIgnoreLabels(parseCSV(ignoreLabels));
        }

        environment.jersey().register(configuration.isTestMode()? new EmptyAddressResource() : new AddressResource(qasSearch));
        environment.jersey().register(configuration.isTestMode()? new EmptySearchResultsResource() : new SearchResultsResource(qasSearch));

        environment.jersey().register(new AddressLookupStatusResource(qasSearch));

        environment.healthChecks().register("SOAP_ServiceCheck", new SoapServiceCheck(qasSearch, configuration.getMode()));

        // "Warm" up CXF.
        List<Address> addresses = qasSearch.getAddresses("20","SW20 9AT");

        if(addresses != null && !addresses.isEmpty()) {
            qasSearch.getAddress(addresses.get(0).getMoniker());
        }
    }

    protected List<String> parseCSV(String csv) {
        String[] entries = csv.split(",");
        List<String> result = new LinkedList<>();
        for (String entry : entries) result.add(entry.trim());
        return result;
    }

}
