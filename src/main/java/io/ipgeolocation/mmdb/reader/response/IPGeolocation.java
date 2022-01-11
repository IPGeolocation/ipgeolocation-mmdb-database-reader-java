package io.ipgeolocation.mmdb.reader.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.base.Strings.nullToEmpty;
import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.isNull;

public class IPGeolocation {
    private Country country = null;
    private Place state = null;
    private Place district = null;
    private Place city = null;
    private String zipCode = "";
    private String latitude = "";
    private String longitude = "";
    private String geoNameId = "";
    private String timeZoneName = "";
    private String isp = "";
    private String connectionType = "";
    private String organization = "";
    private String asNumber = "";

    private final List<String> euCountryCodeISO2List = Arrays.asList("DE", "HR", "IT", "FR", "PL", "ES", "SE", "RO",
            "BG", "GR", "NL", "HU", "CZ", "AT", "IE", "DK", "BE", "PT", "FI", "SI", "CY", "MT", "LT", "SK", "LV", "EE");

    public IPGeolocation() {
    }

    public IPGeolocation(Country country, Place state, Place district, Place city, String zipCode, String latitude,
                         String longitude, String geoNameId, String timeZoneName, String isp, String connectionType,
                         String organization, String asNumber) {
        checkArgument(!isNull(country), "'country' must not be null.");

        this.country = country;
        this.state = state;
        this.district = district;
        this.city = city;
        this.zipCode = nullToEmpty(zipCode);

        if (!isNullOrEmpty(latitude)) {
            this.latitude = String.format("%.5f", Double.parseDouble(latitude));
        }

        if (!isNullOrEmpty(longitude)) {
            this.longitude = String.format("%.5f", Double.parseDouble(longitude));
        }

        this.geoNameId = nullToEmpty(geoNameId);
        this.timeZoneName = nullToEmpty(timeZoneName);
        this.isp = nullToEmpty(isp);
        this.connectionType = nullToEmpty(connectionType);
        this.organization = nullToEmpty(organization);
        this.asNumber = isNullOrEmpty(asNumber) ? "" : String.format("AS%s", asNumber);
    }

    public Map<String, Object> getCompleteGeolocationMap(String lang, DatabaseVersion databaseVersion) {
        checkArgument(!isNullOrEmpty(lang), "'lang' must not be empty or null.");

        Map<String, Object> responseMap = new LinkedHashMap<>();

        responseMap.put("continent_code", country.getContinentCode());
        responseMap.put("continent_name", country.getContinentName().getName(lang));
        responseMap.put("country_code2", country.getCountryCodeISO2());
        responseMap.put("country_code3", country.getCountryCodeISO3());
        responseMap.put("country_name", country.getCountryName().getName(lang));

        if (isNull(country.getCountryCapital())) {
            responseMap.put("country_capital", "");
        } else {
            responseMap.put("country_capital", country.getCountryCapital().getName(lang));
        }

        if (DatabaseVersion.IP_TO_CITY_DATABASES.contains(databaseVersion) ||
                DatabaseVersion.IP_TO_CITY_AND_ISP_DATABASES.contains(databaseVersion)) {
            responseMap.put("state_prov", state.getName(lang));
            responseMap.put("district", district.getName(lang));
            responseMap.put("city", city.getName(lang));
            responseMap.put("zipcode", zipCode);
            responseMap.put("latitude", latitude);
            responseMap.put("longitude", longitude);
            responseMap.put("geoname_id", geoNameId);
        }

        responseMap.put("is_eu", euCountryCodeISO2List.contains(country.getCountryCodeISO2()));
        responseMap.put("calling_code", country.getCallingCode());
        responseMap.put("country_tld", country.getTld());
        responseMap.put("languages", country.getLanguages());
        responseMap.put("country_flag", country.getFlagUrl());

        if (DatabaseVersion.DATABASES_WITH_ISP.contains(databaseVersion)) {
            responseMap.put("isp", isp);
            responseMap.put("connection_type", connectionType);
            responseMap.put("organization", organization);
            responseMap.put("asn", asNumber);
        }

        responseMap.put("currency", country.getCurrencyMap());

        if (DatabaseVersion.IP_TO_CITY_DATABASES.contains(databaseVersion) ||
                DatabaseVersion.IP_TO_CITY_AND_ISP_DATABASES.contains(databaseVersion)) {
            responseMap.put("time_zone", getTimeZoneMap());
        }

        return responseMap;
    }

    private Map<String, Object> getTimeZoneMap() {
        Map<String, Object> responseMap = new LinkedHashMap<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSZ");
        ZoneId zoneId = ZoneId.of(timeZoneName);
        LocalDateTime localDateTimeNow = LocalDateTime.now(zoneId);
        ZoneOffset zoneOffset = zoneId.getRules().getOffset(localDateTimeNow);
        ZonedDateTime zonedDateTimeNow = ZonedDateTime.now(zoneId);

        responseMap.put("name", zoneId.getId());
        responseMap.put("offset", BigDecimal.valueOf(zoneOffset.getTotalSeconds() / 3600));
        responseMap.put("current_time", zonedDateTimeNow.format(dateTimeFormatter));
        responseMap.put("current_time_unix", zonedDateTimeNow.toEpochSecond());
        responseMap.put("is_dst", zoneId.getRules().isDaylightSavings(zonedDateTimeNow.toInstant()));
        responseMap.put("dst_savings", BigDecimal.valueOf(zoneId.getRules().getDaylightSavings(zonedDateTimeNow.toInstant()).toMillis() / 3600));

        return responseMap;
    }
}
