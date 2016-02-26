package uk.gov.hmpo.passport.renewal.address.services;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class AddressLookupConfiguration extends Configuration {

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;

    @JsonProperty
    private String mode;
    @JsonProperty
    private String username;
    @JsonProperty
    private String password;
    @JsonProperty
    private String country;
    @JsonProperty
    private String layout;
    @JsonProperty
    private String uprnLabel;
    @JsonProperty
    private String postcodeLabel;
    @JsonProperty
    private String townLabel;
    @JsonProperty
    private String eastingLabel;
    @JsonProperty
    private String northingLabel;
    @JsonProperty
    private String countryLabel;
    @JsonProperty
    private String dependentLocalityLabel;
    @JsonProperty
    private String dependentThroughfareLabel;
    @JsonProperty
    private String administrativeAreaLabel;
    @JsonProperty
    private String ignoreLabels;
    @JsonProperty
    private String localAuthorityUpdateDateLabel;
    @JsonProperty
    private String royalMailUpdateDateLabel;

    public String getMode() {
        return mode;
    }

    public boolean isTestMode() {
        return mode.equals("TEST");
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getIgnoreLabels() {
        return ignoreLabels;
    }

    public void setIgnoreLabels(String ignoreLabels) {
        this.ignoreLabels = ignoreLabels;
    }

    public String getEastingLabel() {
        return eastingLabel;
    }

    public void setEastingLabel(String eastingLabel) {
        this.eastingLabel = eastingLabel;
    }

    public String getNorthingLabel() {
        return northingLabel;
    }

    public void setNorthingLabel(String northingLabel) {
        this.northingLabel = northingLabel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getUprnLabel() {
        return uprnLabel;
    }

    public void setUprnLabel(String uprnLabel) {
        this.uprnLabel = uprnLabel;
    }

    public String getPostcodeLabel() {
        return postcodeLabel;
    }

    public void setPostcodeLabel(String postcodeLabel) {
        this.postcodeLabel = postcodeLabel;
    }

    public String getTownLabel() {
        return townLabel;
    }

    public void setTownLabel(String townLabel) {
        this.townLabel = townLabel;
    }

    public String getCountryLabel() {
        return countryLabel;
    }

    public void setCountryLabel(String countryLabel) {
        this.countryLabel = countryLabel;
    }

    public String getDependentLocalityLabel() {
        return dependentLocalityLabel;
    }

    public void setDependentLocalityLabel(String dependentLocalityLabel) {
        this.dependentLocalityLabel = dependentLocalityLabel;
    }

    public String getDependentThroughfareLabel() {
        return dependentThroughfareLabel;
    }

    public void setDependentThroughfareLabel(String dependentThroughfareLabel) {
        this.dependentThroughfareLabel = dependentThroughfareLabel;
    }

    public String getAdministrativeAreaLabel() {
        return administrativeAreaLabel;
    }

    public void setAdministrativeAreaLabel(String administrativeAreaLabel) {
        this.administrativeAreaLabel = administrativeAreaLabel;
    }

    public String getLocalAuthorityUpdateDateLabel() {
        return localAuthorityUpdateDateLabel;
    }

    public void setLocalAuthorityUpdateDateLabel(
            String localAuthorityUpdateDateLabel) {
        this.localAuthorityUpdateDateLabel = localAuthorityUpdateDateLabel;
    }

    public String getRoyalMailUpdateDateLabel() {
        return royalMailUpdateDateLabel;
    }

    public void setRoyalMailUpdateDateLabel(String royalMailUpdateDateLabel) {
        this.royalMailUpdateDateLabel = royalMailUpdateDateLabel;
    }

}
