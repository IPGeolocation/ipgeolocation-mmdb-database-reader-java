package io.ipgeolocation.mmdb.reader.response;

import com.maxmind.db.MaxMindDbConstructor;
import com.maxmind.db.MaxMindDbParameter;

import java.math.BigInteger;

public class IPCityAndISPResponse extends IPGeolocation {

    @MaxMindDbConstructor
    IPCityAndISPResponse(
            @MaxMindDbParameter(name = "continent_code") String continentCode,
            @MaxMindDbParameter(name = "continent_name") Place continentName,
            @MaxMindDbParameter(name = "country_code2") String countryCodeISO2,
            @MaxMindDbParameter(name = "country_code3") String countryCodeISO3,
            @MaxMindDbParameter(name = "country_name") Place countryName,
            @MaxMindDbParameter(name = "country_capital") Place countryCapital,
            @MaxMindDbParameter(name = "state_prov") Place state,
            @MaxMindDbParameter(name = "district") Place district,
            @MaxMindDbParameter(name = "city") Place city,
            @MaxMindDbParameter(name = "zip_code") String zipCode,
            @MaxMindDbParameter(name = "latitude") float latitude,
            @MaxMindDbParameter(name = "longitude") float longitude,
            @MaxMindDbParameter(name = "geoname_id") String geoNameId,
            @MaxMindDbParameter(name = "time_zone") String timeZoneName,
            @MaxMindDbParameter(name = "isp") String isp,
            @MaxMindDbParameter(name = "connection_type") String connectionType,
            @MaxMindDbParameter(name = "organization") String organization,
            @MaxMindDbParameter(name = "as_number") BigInteger asNumber,
            @MaxMindDbParameter(name = "currency") Currency currency,
            @MaxMindDbParameter(name = "calling_code") String callingCode,
            @MaxMindDbParameter(name = "languages") String languages,
            @MaxMindDbParameter(name = "tld") String tld) {
        super(new Country(continentCode, continentName, countryCodeISO2, countryCodeISO3, countryName, countryCapital,
                        currency.getCode(), currency.getName(), currency.getSymbol(), callingCode, tld, languages),
        state, district, city, zipCode, String.format("%.5f", latitude), String.format("%.5f", longitude),
                geoNameId, timeZoneName, isp, connectionType, organization, asNumber.toString());
    }
}
