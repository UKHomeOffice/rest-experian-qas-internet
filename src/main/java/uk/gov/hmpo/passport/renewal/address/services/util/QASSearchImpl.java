package uk.gov.hmpo.passport.renewal.address.services.util;

import com.qas.ondemand_2011_03.*;
import org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_secext_1_0.SecurityHeaderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmpo.passport.renewal.address.services.core.Address;

import javax.xml.ws.Holder;
import javax.xml.ws.soap.SOAPFaultException;
import java.util.*;

public class QASSearchImpl implements QASSearch {

    final Logger log = LoggerFactory.getLogger(QASSearchImpl.class);

    private final QAQueryHeader qaQueryHeader;

    private final String country;

    private final String layout;
    private final QASOnDemandIntermediary qasOnDemandIntermediary;
    private String postcodeLabel = "postcode";
    private String townLabel = "town";
    private String uprnLabel = "uprn";
    private String eastingLabel = "eastings";
    private String northingLabel = "northings";
    private String countryLabel = "country";
    private String dependentLocalityLabel = "dependent locality";
    private String dependentThroughfareLabel = "dependent throughfare";
    private String administrativeAreaLabel = "administrative area english";
    private String localAuthorityUpdateDateLabel = "last update date";
    private String royalMailUpdateDateLabel = "paf process date";
    private Collection<String> ignoreLabels;

    public QASSearchImpl(String username, String password, String country,
                         String layout, QASOnDemandIntermediary qasOnDemandIntermediary) {
        QAQueryHeader headerObj = new QAQueryHeader();
        SecurityHeaderType securityHeader = new SecurityHeaderType();
        QAAuthentication qaAuth = new QAAuthentication();
        qaAuth.setUsername(username);
        qaAuth.setPassword(password);
        headerObj.setQAAuthentication(qaAuth);
        headerObj.setSecurity(securityHeader);
        qaQueryHeader = headerObj;
        this.country = country;
        this.layout = layout;
        this.qasOnDemandIntermediary = qasOnDemandIntermediary;
    }

    public void setIgnoreLabels(Collection<String> ignoreLabels) {
        this.ignoreLabels = new HashSet<String>();
        for (String label : ignoreLabels) {
            if (label != null) {
                this.ignoreLabels.add(label.trim().toLowerCase());
            }
        }
    }

    public void setCountryLabel(String countryLabel) {
        this.countryLabel = countryLabel != null ? countryLabel.toLowerCase()
                : null;
    }

    public void setDependentLocalityLabel(String dependentLocalityLabel) {
        this.dependentLocalityLabel = dependentLocalityLabel != null ? dependentLocalityLabel
                .toLowerCase() : null;
    }

    public void setDependentThroughfareLabel(String dependentThroughfareLabel) {
        this.dependentThroughfareLabel = dependentThroughfareLabel != null ? dependentThroughfareLabel
                .toLowerCase() : null;
    }

    public void setEastingLabel(String eastingsLabel) {
        this.eastingLabel = eastingsLabel != null ? eastingsLabel.toLowerCase()
                : null;
    }

    public void setNorthingLabel(String northingsLabel) {
        this.northingLabel = northingsLabel != null ? northingsLabel
                .toLowerCase() : null;
    }

    public void setPostcodeLabel(String postcodeLabel) {
        this.postcodeLabel = postcodeLabel != null ? postcodeLabel
                .toLowerCase() : null;
    }

    public void setTownLabel(String townLabel) {
        this.townLabel = townLabel != null ? townLabel.toLowerCase() : null;
    }

    public void setUprnLabel(String uprnLabel) {
        this.uprnLabel = uprnLabel != null ? uprnLabel.toLowerCase() : null;
    }

    public void setAdministrativeAreaLabel(String administrativeAreaLabel) {
        this.administrativeAreaLabel = administrativeAreaLabel != null ? administrativeAreaLabel
                .toLowerCase() : null;
    }

    public void setLocalAuthorityUpdateDateLabel(
            String localAuthorityUpdateDateLabel) {
        this.localAuthorityUpdateDateLabel = localAuthorityUpdateDateLabel != null ? localAuthorityUpdateDateLabel
                .toLowerCase() : null;
    }

    public void setRoyalMailUpdateDateLabel(String royalMailUpdateDateLabel) {
        this.royalMailUpdateDateLabel = royalMailUpdateDateLabel != null ? royalMailUpdateDateLabel
                .toLowerCase() : null;
    }

    protected QAPortType getQASOnDemand() {
        return qasOnDemandIntermediary.getQAPortType();
    }

    protected Holder<QAInformation> getInformationHolder() {
        return new Holder<QAInformation>(new QAInformation());
    }

    public String checkHealth() {
        QACanSearch qaCanSearch = new QACanSearch();
        qaCanSearch.setCountry(country);

        EngineType engineType = new EngineType();
        engineType.setValue(EngineEnumType.SINGLELINE);
        engineType.setFlatten(true);

        qaCanSearch.setEngine(engineType);
        qaCanSearch.setLayout(layout);
        QASearchOk qaSearchOk = getQASOnDemand().doCanSearch(qaCanSearch,
                qaQueryHeader, getInformationHolder());
        return qaSearchOk.getIsOk() ? null : qaSearchOk.getErrorMessage();
    }

    public boolean canSearch() {
        QACanSearch qaCanSearch = new QACanSearch();
        qaCanSearch.setCountry(country);

        EngineType engineType = new EngineType();
        engineType.setValue(EngineEnumType.SINGLELINE);
        engineType.setFlatten(true);

        qaCanSearch.setEngine(engineType);
        qaCanSearch.setLayout(layout);
        QASearchOk qaSearchOk = getQASOnDemand().doCanSearch(qaCanSearch,
                qaQueryHeader, getInformationHolder());
        return qaSearchOk.getIsOk();
    }

    public Address getAddress(String id) {

        QAPortType client = getQASOnDemand();

        QAGetAddress qaGetAddress = new QAGetAddress();
        qaGetAddress.setMoniker(id);
        qaGetAddress.setLayout(layout);

        com.qas.ondemand_2011_03.Address qaAddressHolder;

        try {
            qaAddressHolder = client.doGetAddress(
                    qaGetAddress, qaQueryHeader, getInformationHolder());
        } catch (SOAPFaultException e) {

            log.error(String.format("SOAP Exception. Code: %s, Name: %s, Detail: %s",
                    e.getFault().getFaultCode(),
                    e.getFault().getFaultCodeAsName(),
                    e.getFault().getDetail(), e
                    ) );
            return null;
        }

        QAAddressType qaAddress = qaAddressHolder.getQAAddress();

        Address address = new Address();
        address.setMoniker(id);
        List<String> lines = new LinkedList<String>();
        List<AddressLineType> qaLines = qaAddress.getAddressLine();
        for (AddressLineType qaLine : qaLines) {
            String label = qaLine.getLabel() != null ? qaLine.getLabel()
                    .toLowerCase() : null;
            System.out.println(qaLine.getLabel() + "=" + qaLine.getLine());
            if (uprnLabel.equals(label)) {
                address.setUprn(qaLine.getLine());
                continue;
            }
            if (townLabel.equals(label)) {
                address.setTown(qaLine.getLine());
                continue;
            }
            if (postcodeLabel.equals(label)) {
                address.setPostcode(qaLine.getLine());
                continue;
            }
            if (countryLabel.equals(label)) {
                address.setCountry(qaLine.getLine());
                continue;
            }
            if (eastingLabel.equals(label)) {
                address.setEasting(qaLine.getLine());
                continue;
            }
            if (northingLabel.equals(label)) {
                address.setNorthing(qaLine.getLine());
                continue;
            }
            if (dependentLocalityLabel.equals(label)) {
                address.setDependentLocality(qaLine.getLine());
                continue;
            }
            if (dependentThroughfareLabel.equals(label)) {
                address.setDependentThroughfare(qaLine.getLine());
                continue;
            }
            if (administrativeAreaLabel.equals(label)) {
                address.setAdministrativeArea(qaLine.getLine());
                continue;
            }
            if (localAuthorityUpdateDateLabel.equals(label)) {
                address.setLocalAuthorityUpdateDate(qaLine.getLine());
                continue;
            }
            if (royalMailUpdateDateLabel.equals(label)) {
                address.setRoyalMailUpdateDate(qaLine.getLine());
                continue;
            }
            if (ignoreLabels != null && ignoreLabels.contains(label)) {
                continue;
            }
            String line = qaLine.getLine();
            if (line != null && line.length() != 0) {
                lines.add(line);
            }
        }
        address.setLines(lines);

        return address;
    }

    public List<Address> getAddresses(String houseNumber, String postcode) {
        if (postcode == null || "".equals(postcode)) {
            return Collections.emptyList();
        }

        QAPortType client = getQASOnDemand();

        QASearch qaSearch = new QASearch();

        qaSearch.setCountry(country);

        // Set engine type
        EngineType engineType = new EngineType();
        engineType.setValue(EngineEnumType.SINGLELINE);
        engineType.setFlatten(true);
        engineType.setIntensity(EngineIntensityType.EXACT);
        qaSearch.setEngine(engineType);

        qaSearch.setLayout(layout);
        qaSearch.setFormattedAddressInPicklist(false);

        // Set search term
        StringBuilder searchTerm = new StringBuilder();
        if (houseNumber != null && houseNumber.length() > 0) {
            searchTerm.append(houseNumber);
            searchTerm.append(",");
        }

        // Append @x as search operator (http://support.qas.com/address_searching_faqs_1561.htm)
        searchTerm.append(postcode + " @x");
        qaSearch.setSearch(searchTerm.toString());

        QASearchResult qaSearchResult;

        try {
            qaSearchResult = client.doSearch(qaSearch,
                    qaQueryHeader, getInformationHolder());
        } catch (SOAPFaultException e) {
            log.error(String.format("SOAP Exception. Code: %s, Name: %s, Detail: %s",
                    e.getFault().getFaultCode(),
                    e.getFault().getFaultCodeAsName(),
                    e.getFault().getDetail(), e
            ) );
            return null;
        }


        QAPicklistType picklist = qaSearchResult.getQAPicklist();
        List<PicklistEntryType> entries = picklist.getPicklistEntry();

        List<Address> result = new LinkedList<Address>();

        if (entries.size() == 1 && entries.get(0).getCanStep()) {
            Address address = getAddress(entries.get(0).getMoniker());
            result.add(address);
        } else {
            for (PicklistEntryType entry : entries) {
                if (!"".equals(entry.getMoniker())) {
                    Address address = new Address();
                    address.setMoniker(entry.getMoniker());
                    address.setPartial(entry.getPartialAddress());
                    address.setPostcode(entry.getPostcode());
                    result.add(address);
                }
            }
        }

        return result;
    }
}
