package io.ipgeolocation.mmdb.reader.response;

import com.maxmind.db.MaxMindDbConstructor;
import com.maxmind.db.MaxMindDbParameter;

public class IPCountryResponse extends IPGeolocation {

    @MaxMindDbConstructor
    public IPCountryResponse(
            @MaxMindDbParameter(name = "continent_code") String continentCode,
            @MaxMindDbParameter(name = "continent_name") Place continentName,
            @MaxMindDbParameter(name = "country_code2") String countryCodeISO2,
            @MaxMindDbParameter(name = "country_code3") String countryCodeISO3,
            @MaxMindDbParameter(name = "country_name") Place countryName,
            @MaxMindDbParameter(name = "country_capital") Place countryCapital,
            @MaxMindDbParameter(name = "currency") Currency currency,
            @MaxMindDbParameter(name = "calling_code") String callingCode,
            @MaxMindDbParameter(name = "languages") String languages,
            @MaxMindDbParameter(name = "tld") String tld) {
        super(new Country(continentCode, continentName, countryCodeISO2, countryCodeISO3, countryName, countryCapital,
                        currency.getCode(), currency.getName(), currency.getSymbol(), callingCode, tld, languages),
        null, null, null, null, null, null, null, null, null, null, null, null);
    }
}
