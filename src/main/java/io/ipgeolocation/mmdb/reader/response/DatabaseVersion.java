package io.ipgeolocation.mmdb.reader.response;

import java.util.Arrays;
import java.util.List;

public enum DatabaseVersion {
    DB_I, DB_II, DB_III, DB_IV, DB_V, DB_VI, DB_VII;

    public static final List<DatabaseVersion> IP_TO_COUNTRY_DATABASES = Arrays.asList(DB_I, DB_V),
    IP_TO_CITY_DATABASES = Arrays.asList(DB_II, DB_VI),
    IP_TO_CITY_AND_ISP_DATABASES = Arrays.asList(DB_IV, DB_VII),
    DATABASES_WITH_ISP = Arrays.asList(DB_III, DB_IV, DB_VII),
    DATABASES_WITH_PROXY = Arrays.asList(DB_V, DB_VI, DB_VII);
}
