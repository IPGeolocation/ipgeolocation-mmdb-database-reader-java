package io.ipgeolocation.mmdb.reader;

import com.maxmind.db.NoCache;
import com.maxmind.db.NodeCache;
import com.maxmind.db.Reader;
import io.ipgeolocation.mmdb.reader.response.*;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.isNull;

public class MMDBLookup {

    public static void main(String[] args) {
        String ipGeolocationMMDBFilePath = String.format("%s/conf/db-ipgeolocation/db-ip-geolocation.mmdb", System.getenv("HOME"));
        String ipSecurityMMDBFilePath = String.format("%s/conf/db-ipgeolocation/db-ip-security.mmdb", System.getenv("HOME"));
        Path ipGeolocationMMDBPath = Paths.get(ipGeolocationMMDBFilePath);

        checkState(Files.isRegularFile(ipGeolocationMMDBPath) && Files.exists(ipGeolocationMMDBPath),
                String.format("%s is missing.", ipGeolocationMMDBFilePath));

        DatabaseVersion databaseVersion = DatabaseVersion.DB_II;
        NodeCache noCache = NoCache.getInstance();

        try {
            InetAddress inetAddress = InetAddress.getByName("1.1.1.1");
            IPGeolocation ipGeolocation = null;
            IPSecurity ipSecurity = null;

            System.out.println("Initializing ip-geolocation MMDB reader.");
            Reader ipGeolocationMMDBReader = new Reader(ipGeolocationMMDBPath.toFile(), noCache);

            if (DatabaseVersion.IP_TO_COUNTRY_DATABASES.contains(databaseVersion)) {
                ipGeolocation = ipGeolocationMMDBReader.get(inetAddress, IPCountryResponse.class);
            } else if (databaseVersion == DatabaseVersion.DB_III) {
                ipGeolocation = ipGeolocationMMDBReader.get(inetAddress, IPISPResponse.class);
            } else if (DatabaseVersion.IP_TO_CITY_DATABASES.contains(databaseVersion)) {
                ipGeolocation = ipGeolocationMMDBReader.get(inetAddress, IPCityResponse.class);
            } else if (DatabaseVersion.IP_TO_CITY_AND_ISP_DATABASES.contains(databaseVersion)) {
                ipGeolocation = ipGeolocationMMDBReader.get(inetAddress, IPCityAndISPResponse.class);
            }

            if (DatabaseVersion.DATABASES_WITH_PROXY.contains(databaseVersion)) {
                Path ipSecurityMMDBPath = Paths.get(ipSecurityMMDBFilePath);

                checkState(Files.isRegularFile(ipSecurityMMDBPath) && Files.exists(ipSecurityMMDBPath),
                        String.format("%s is missing.", ipSecurityMMDBPath));

                System.out.println("Initializing ip-geolocation MMDB reader.");
                Reader ipSecurityMMDBReader = new Reader(ipSecurityMMDBPath.toFile(), noCache);

                ipSecurity = ipSecurityMMDBReader.get(inetAddress, IPSecurity.class);
            }

            if (!isNull(ipGeolocation)) {
                System.out.println(ipGeolocation.getCompleteGeolocationMap("en", databaseVersion));
            }

            if (!isNull(ipSecurity)) {
                System.out.println("Threat score: " + ipSecurity.getThreatScore());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
