package uk.gov.hmpo.passport.renewal.address.services.util;

import uk.gov.hmpo.passport.renewal.address.services.core.Address;

import java.util.List;

public interface QASSearch {

    String checkHealth();

    boolean canSearch();

    List<Address> getAddresses(String houseNumber, String postcode);

    Address getAddress(String moniker);
}
