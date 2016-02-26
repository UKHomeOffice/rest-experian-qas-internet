package uk.gov.hmpo.passport.renewal.address.services.core;

import java.util.List;

public class Address {

    private String moniker;

    private String uprn;

    private List<String> lines;

    private String town;

    private String postcode;

    private String easting;

    private String northing;

    private String country;

    private String dependentLocality;

    private String dependentThroughfare;

    private String administrativeArea;

    private String localAuthorityUpdateDate;

    private String royalMailUpdateDate;

    private String partial;

    public String getUprn() {
        return uprn;
    }

    public void setUprn(String uprn) {
        this.uprn = uprn;
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getMoniker() {
        return moniker;
    }

    public void setMoniker(String moniker) {
        this.moniker = moniker;
    }

    public String getPartial() {
        return partial;
    }

    public void setPartial(String partial) {
        this.partial = partial;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getEasting() {
        return easting;
    }

    public void setEasting(String eastings) {
        this.easting = eastings;
    }

    public String getNorthing() {
        return northing;
    }

    public void setNorthing(String northings) {
        this.northing = northings;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDependentLocality() {
        return dependentLocality;
    }

    public void setDependentLocality(String dependentLocality) {
        this.dependentLocality = dependentLocality;
    }

    public String getDependentThroughfare() {
        return dependentThroughfare;
    }

    public void setDependentThroughfare(String dependentThroughfare) {
        this.dependentThroughfare = dependentThroughfare;
    }

    public String getAdministrativeArea() {
        return administrativeArea;
    }

    public void setAdministrativeArea(String administrativeArea) {
        this.administrativeArea = administrativeArea;
    }

    public String getLocalAuthorityUpdateDate() {
        return localAuthorityUpdateDate;
    }

    public void setLocalAuthorityUpdateDate(String localAuthorityUpdateDate) {
        this.localAuthorityUpdateDate = localAuthorityUpdateDate;
    }

    public String getRoyalMailUpdateDate() {
        return royalMailUpdateDate;
    }

    public void setRoyalMailUpdateDate(String royalMailUpdateDate) {
        this.royalMailUpdateDate = royalMailUpdateDate;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (uprn != null) {
            sb.append("[uprn=" + uprn + "]");
        }
        if (partial != null) {
            sb.append(partial);
        } else if (lines != null) {
            for (String line : lines) {
                sb.append(line + ", ");
            }
            if (town != null) {
                sb.append(town + ", ");
            }
            sb.append(postcode);
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (administrativeArea != null ? !administrativeArea.equals(address.administrativeArea) : address.administrativeArea != null)
            return false;
        if (country != null ? !country.equals(address.country) : address.country != null) return false;
        if (dependentLocality != null ? !dependentLocality.equals(address.dependentLocality) : address.dependentLocality != null)
            return false;
        if (dependentThroughfare != null ? !dependentThroughfare.equals(address.dependentThroughfare) : address.dependentThroughfare != null)
            return false;
        if (easting != null ? !easting.equals(address.easting) : address.easting != null) return false;
        if (lines != null ? !lines.equals(address.lines) : address.lines != null) return false;
        if (localAuthorityUpdateDate != null ? !localAuthorityUpdateDate.equals(address.localAuthorityUpdateDate) : address.localAuthorityUpdateDate != null)
            return false;
        if (moniker != null ? !moniker.equals(address.moniker) : address.moniker != null) return false;
        if (northing != null ? !northing.equals(address.northing) : address.northing != null) return false;
        if (partial != null ? !partial.equals(address.partial) : address.partial != null) return false;
        if (postcode != null ? !postcode.equals(address.postcode) : address.postcode != null) return false;
        if (royalMailUpdateDate != null ? !royalMailUpdateDate.equals(address.royalMailUpdateDate) : address.royalMailUpdateDate != null)
            return false;
        if (town != null ? !town.equals(address.town) : address.town != null) return false;
        if (uprn != null ? !uprn.equals(address.uprn) : address.uprn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = moniker != null ? moniker.hashCode() : 0;
        result = 31 * result + (uprn != null ? uprn.hashCode() : 0);
        result = 31 * result + (lines != null ? lines.hashCode() : 0);
        result = 31 * result + (town != null ? town.hashCode() : 0);
        result = 31 * result + (postcode != null ? postcode.hashCode() : 0);
        result = 31 * result + (easting != null ? easting.hashCode() : 0);
        result = 31 * result + (northing != null ? northing.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (dependentLocality != null ? dependentLocality.hashCode() : 0);
        result = 31 * result + (dependentThroughfare != null ? dependentThroughfare.hashCode() : 0);
        result = 31 * result + (administrativeArea != null ? administrativeArea.hashCode() : 0);
        result = 31 * result + (localAuthorityUpdateDate != null ? localAuthorityUpdateDate.hashCode() : 0);
        result = 31 * result + (royalMailUpdateDate != null ? royalMailUpdateDate.hashCode() : 0);
        result = 31 * result + (partial != null ? partial.hashCode() : 0);
        return result;
    }
}
