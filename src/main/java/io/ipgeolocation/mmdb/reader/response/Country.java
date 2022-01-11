package io.ipgeolocation.mmdb.reader.response;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.base.Strings.nullToEmpty;
import static java.util.Objects.isNull;

public class Country {
    private final String continentCode;
    private final Place continentName;
    private final String countryCodeISO2;
    private final String countryCodeISO3;
    private final Place countryName;
    private final Place countryCapital;
    private final String currencyCode;
    private final String currencyName;
    private final String currencySymbol;
    private final String callingCode;
    private final String tld;
    private final String languages;
    private final String flagUrl;

    public Country(String continentCode, Place continentName, String countryCodeISO2, String countryCodeISO3,
                   Place countryName, Place countryCapital, String currencyCode, String currencyName,
                   String currencySymbol, String callingCode, String tld, String languages) {
        checkArgument(!isNullOrEmpty(continentCode), "'continentCode' must not be empty or null.");
        checkArgument(!isNull(continentName), "'continentName' must not be null.");
        checkArgument(!isNullOrEmpty(countryCodeISO2), "'countryCode2' must not be empty or null.");
        checkArgument(!isNullOrEmpty(countryCodeISO3), "'countryCodeISO3' must not be empty or null.");
        checkArgument(!isNull(countryName), "'countryName' must not be null.");

        this.continentCode = continentCode;
        this.continentName = continentName;
        this.countryCodeISO2 = countryCodeISO2;
        this.countryCodeISO3 = countryCodeISO3;
        this.countryName = countryName;
        this.countryCapital = countryCapital;
        this.currencyCode = nullToEmpty(currencyCode);
        this.currencyName = nullToEmpty(currencyName);
        this.currencySymbol = nullToEmpty(currencySymbol);
        this.callingCode = nullToEmpty(callingCode);
        this.tld = nullToEmpty(tld);
        this.languages = nullToEmpty(languages);
        this.flagUrl = Objects.equals(countryCodeISO2, "ZZ") ? "" : "https://ipgeolocation.io/static/flags/${countryCodeISO2.toLowerCase()}_64.png";
    }

    public String getContinentCode() {
        return continentCode;
    }

    public Place getContinentName() {
        return continentName;
    }

    public String getCountryCodeISO2() {
        return countryCodeISO2;
    }

    public String getCountryCodeISO3() {
        return countryCodeISO3;
    }

    public Place getCountryName() {
        return countryName;
    }

    public Place getCountryCapital() {
        return countryCapital;
    }

    public String getCallingCode() {
        return callingCode;
    }

    public String getTld() {
        return tld;
    }

    public String getLanguages() {
        return languages;
    }

    public String getFlagUrl() {
        return flagUrl;
    }

    final Map<String, Object> getCurrencyMap() {
        Map<String, Object> responseMap = new LinkedHashMap<>();

        responseMap.put("code", currencyCode);
        responseMap.put("name", currencyName);
        responseMap.put("symbol", currencySymbol);

        return responseMap;
    }
}
